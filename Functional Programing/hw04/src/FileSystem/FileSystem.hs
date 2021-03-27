{-# LANGUAGE Rank2Types #-}

-- | File system file
module FileSystem.FileSystem (cd, ls, file, getDirectory, contents', FS(..)) where

import Lens.Micro (Lens', Traversal', each, filtered, lens, (^?))
import System.Directory (doesDirectoryExist, listDirectory, withCurrentDirectory)

-- | File system
data FS
  = Dir
      { name :: FilePath, -- название папки, не полный путь
        contents :: [FS]
      }
  | File
      { name :: FilePath -- название файла, не полный путь
      }

-- | Scan file system and make FS class
getDirectory :: FilePath -> IO FS
getDirectory path = do
  isDirectoryExist <- doesDirectoryExist path
  if isDirectoryExist
    then do
      files <- listDirectory path
      fileSystem <- withCurrentDirectory path (mapM getDirectory files)
      return (Dir path fileSystem)
    else ioError (userError "This is not a directory")

-- | Name lens
name' :: Lens' FS FilePath
name' = lens name $ \fileSystem input -> fileSystem {name = input}

-- | Content lens
contents' :: Lens' FS [FS]
contents' = lens contents $ \fileSystem input -> fileSystem {contents = input}

-- | Filename traversal
_file :: Traversal' FS FilePath
_file fileFunction (File file') = File <$> fileFunction file'
_file _ another = pure another

-- | Directory name traversal
_directoryName :: Traversal' FS FilePath
_directoryName directoryFunction (Dir name'' package) = Dir <$> directoryFunction name'' <*> pure package
_directoryName _ another = pure another

-- | Directory content traversal
_directoryContent :: Traversal' FS [FS]
_directoryContent functionDirectory (Dir name'' package) = Dir name'' <$> functionDirectory package
_directoryContent _ another = pure another

-- | Focus lens on folder with name
cd :: FilePath -> Traversal' FS FS
cd path = _directoryContent . each . filtered (elem path . (^? _directoryName))

-- | Focus on names on folder
ls :: Traversal' FS FilePath
ls = _directoryContent . each . name'

-- | Focus on file name, if exists
file :: FilePath -> Traversal' FS FilePath
file path = _directoryContent . each . filtered (elem path . (^? _file)) . name'