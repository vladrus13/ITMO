module FileSystem (FileSystem.read, save) where

import Control.Monad (when)
import Data.List ((\\))
import Data.Maybe (catMaybes)
import System.Directory
  ( createDirectoryIfMissing,
    doesDirectoryExist,
    doesFileExist,
    getFileSize,
    getModificationTime,
    getPermissions,
    listDirectory,
    makeAbsolute,
    removeDirectory,
    removeFile,
    removePathForcibly,
  )
import System.FilePath.Posix (normalise, takeBaseName, takeFileName, (</>))
import System.IO (hPutStrLn, stderr)
import System.IO.Strict
import Types (DirectoryInfo(..), FileInfo(..), FileSystem(File, Folder), FileName(..))

-- | Read file system from path
read :: String -> FilePath -> IO (Maybe FileSystem)
read flags path = do
  permissions <- getPermissions path
  absolutePath <- makeAbsolute path
  isDirectoryExist <- doesDirectoryExist path
  file'' <- doesFileExist path
  if isDirectoryExist
    then do
      files <- listDirectory path
      filedInCurrentDirectory <- mapM (FileSystem.read flags . (path </>)) files
      let fileList = catMaybes filedInCurrentDirectory
      let directory =
            DirectoryInfo
              { directoryName = takeBaseName path,
                directoryPath = absolutePath,
                directoryPermission = permissions
              }
      pure . pure $ Folder directory fileList
    else
      if file''
        then do
          size <- getFileSize path
          content <- System.IO.Strict.readFile path
          modificationTime <- getModificationTime path
          let file' =
                FileInfo
                  { fileName = takeFileName path,
                    filePath = absolutePath,
                    fileContent = content,
                    filePermission = permissions,
                    fileSize = size,
                    fileTime = modificationTime
                  }
          pure . pure $ File file'
        else hPutStrLn stderr ("Something went wrong: " ++ absolutePath) >> pure Nothing

-- | Save file system
save :: FileSystem -> IO ()
save (File file) = do
  directoryExist <- doesDirectoryExist $ filePath file
  when directoryExist (removeDirectory $ filePath file)
  writeFile (filePath file) (fileContent file)
save (Folder directory xs) = do
  directoryExist <- doesDirectoryExist $ directoryPath directory
  fileExist <- doesFileExist $ directoryPath directory
  when fileExist (removeFile $ directoryPath directory)
  if directoryExist
    then do
      listDirectories <- listDirectory $ directoryPath directory
      let removingSystemList = listDirectories \\ map (show . FileName) xs
      mapM_ (removePathForcibly . (normalise . (</>) (directoryPath directory))) removingSystemList
    else createDirectoryIfMissing True $ directoryPath directory
  mapM_ save xs