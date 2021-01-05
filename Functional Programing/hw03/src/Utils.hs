module Utils (
    removeAt,
    Utils.unlines,
    toDirectory,
    isFile,
    isDirectory,
    isImmediately,
    fileSystemParent,
    fileSystemGetUp,
    fileSystemTo,
    equalsNames,
    fileSystemNewFile,
    fileSystemDeleteFile,
    getDirectoryFullPath,
    walker,
    throwError
) where

import Types (FileSystemContainer, FileSystem(File, Folder), Flags(..), FileSystemAdd(..), DirectoryInfo(..), FileInfo(..))
import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.State (State, get, modify)
import Control.Monad.Trans.Except (ExceptT, throwE)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.Writer (WriterT)
import Errors (Error)
import Data.Time.Clock (UTCTime)
import Data.List.Split ( splitOn )
import Data.List (find)
import Data.Maybe (isNothing)

-- | Remove element from array 'xs' at position 'index'
removeAt :: Int -> [a] -> [a]
removeAt xs index = left ++ right where
    (left, _:right) = splitAt xs index

-- | Make from List lines one line with delimiters \n
unlines :: [String] -> String
unlines [] = []
unlines [x] = x
unlines (l : ls) = l ++ '\n' : Utils.unlines ls

-- | Get directory from File System
toDirectory :: State FileSystemContainer ()
toDirectory = do
  (item, _, _) <- get
  case item of
    (File _) -> modify fileSystemGetUp
    _ -> pure ()

-- | Check, is current path a file
isFile :: FileSystem -> Bool
isFile (File _)     = True
isFile _            = False

-- | Check, is current path a directory
isDirectory :: FileSystem -> Bool
isDirectory (Folder _ _)    = True
isDirectory _               = False

-- | Check, is current file System lazy
isImmediately :: Flags -> Bool 
isImmediately (Flags [flag]) = isNothing (find (== "not_immeditely") (splitOn "-" flag)) 
isImmediately _ = False 

-- | Get parent from current directory
fileSystemParent :: FileSystemContainer -> FileSystemContainer
fileSystemParent root@(_, [], _)   = root
fileSystemParent another        = fileSystemGetUp another

-- | Get a root of current file system
fileSystemGetUp :: FileSystemContainer -> FileSystemContainer
fileSystemGetUp root@(_, [], _)                                                 = root
fileSystemGetUp (name, (FileSystemAdd directoryInfo left right) : bs, flags)    = (Folder directoryInfo (left ++ [name] ++ right), bs, flags)

-- | Move current file system
fileSystemTo :: String -> FileSystemContainer -> Maybe FileSystemContainer
fileSystemTo name fz@(Folder folderInfo items, bs, flags)
 | name == ".." = Just $ fileSystemGetUp fz
 | name == "."  = Just fz
 | otherwise    = case break (equalsNames name) items of
     (_ , []     ) -> Nothing
     (ls, item:rs) -> Just (item, FileSystemAdd folderInfo ls rs:bs, flags)
fileSystemTo _ _ = Nothing

-- | Check of equals names
equalsNames :: String -> FileSystem -> Bool
equalsNames name (Folder DirectoryInfo {directoryName = dname} _)   = name == dname
equalsNames name (File FileInfo {fileName = fname})               = name == fname

-- | Make a new file
fileSystemNewFile :: FileSystem -> FileSystemContainer -> Maybe FileSystemContainer
fileSystemNewFile file (Folder directoryInfo items, bs, flags) = Just (Folder directoryInfo (file : items), bs, flags)
fileSystemNewFile _ _                                   = Nothing

-- | Delete file
fileSystemDeleteFile :: String -> FileSystemContainer -> Maybe FileSystemContainer
fileSystemDeleteFile name (Folder directoryInfo items, bs, flags)  = case break (equalsNames name) items of
    (_, []) ->      Nothing
    (ls , _ : rs) -> Just (Folder directoryInfo (ls ++ rs), bs, flags)
fileSystemDeleteFile _ _                                    = Nothing

-- | Get directory full path
getDirectoryFullPath :: FileSystemContainer -> FilePath
getDirectoryFullPath (Folder directoryInfo _, _, _)            = directoryPath directoryInfo
getDirectoryFullPath (_, FileSystemAdd directoryInfo _ _:_, _) = directoryPath directoryInfo
getDirectoryFullPath _                                         = ""

-- | Walker on directories and finder
walker :: String -> [FileSystem] -> [FileSystem]
walker _ [] = []
walker name [f@(File _)] = [f | equalsNames name f]
walker name (f@(File _) : bs) =
  ([f | equalsNames name f]) ++ walker name bs
walker name [f@(Folder _ xs)] =
  ([f | equalsNames name f]) ++ walker name xs
walker name (f@(Folder _ xs) : bs) =
  ([f | equalsNames name f]) ++ walker name (bs ++ xs)

-- | Throw a error
throwError :: Error -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
throwError =
  lift . lift . throwE