{-# LANGUAGE InstanceSigs #-}

module Types
  ( FileInfo (..),
    DirectoryInfo (..),
    FileSystem (File, Folder),
    FileSystemPath(..),
    FileName(..),
    FileSystemInfo(..),
    PermissionsInfo,
    FileSystemAdd(..),
    Flags(..),
    FileSystemContainer,
    Command(..),
    Optics(..)
  )
where

import Data.List()
import Data.Time.Clock (UTCTime)
import System.Directory.Internal (Permissions (..))

-- | Information about file
data FileInfo = FileInfo
  { fileName :: String,
    filePath :: FilePath,
    fileContent :: !String,
    fileSize :: Integer,
    filePermission :: Permissions,
    fileTime :: UTCTime
  }
  deriving (Show)

-- | Information about directory
data DirectoryInfo = DirectoryInfo
  { directoryName :: String,
    directoryPath :: FilePath,
    directoryPermission :: Permissions
  }
  deriving (Show)

-- | File system
data FileSystem
  = File FileInfo
  | Folder DirectoryInfo [FileSystem]
  deriving (Show)

-- | FileSystem path
newtype FileSystemPath = FileSystemPath FileSystem

-- | File name
newtype FileName = FileName FileSystem

-- | Instance show for name
instance Show FileName where
  show :: FileName -> String
  show (FileName (File file)) = fileName file
  show (FileName (Folder directory _)) = directoryName directory

-- | Instance show for fileSystemPath
instance Show FileSystemPath where
  show :: FileSystemPath -> String
  show (FileSystemPath (File file)) = filePath file
  show (FileSystemPath (Folder directory _)) = directoryPath directory

-- | File on system information
newtype FileSystemInfo = FileSystemInfo FileSystem

-- | Instance for file system information
instance Show FileSystemInfo where
  show :: FileSystemInfo -> String
  show (FileSystemInfo (Folder directory _)) 
    = "Path: " ++ show (directoryPath directory) ++ 
      ". Directory Name: " ++ show (directoryName directory) ++
      ". Permissions: " ++ show (directoryPermission directory)
  show (FileSystemInfo (File file)) 
    = "Path: " ++ show (filePath file) ++
      ". Name: " ++ show (fileName file) ++
      ". Size: " ++ show (fileSize file) ++
      ". Permission: " ++ show (filePermission file) ++
      ". Last modufy time: " ++ show (fileTime file)

-- | Permissions information
newtype PermissionsInfo = PermissionsInfo Permissions

-- | Instance show for Permission information
instance Show PermissionsInfo where
  show :: PermissionsInfo -> String
  show (PermissionsInfo (Permissions readPermission write _ xe)) =
    concat
      [ if readPermission then "r" else " ",
        if write then "w" else " ",
        if xe then "x" else " "
      ]

-- | Added file system
data FileSystemAdd = FileSystemAdd DirectoryInfo [FileSystem] [FileSystem]
  deriving (Show)

-- | Flags for File System
newtype Flags = Flags [String]
  deriving (Show)

-- | Container with path, system and flags
type FileSystemContainer = (FileSystem, [FileSystemAdd], Flags)

-- | Command
data Command
  = Cd String
  | Dir
  | Ls String
  | CreateDir String
  | Cat String
  | CreateFile String
  | Delete String
  | WriteFile String [String]
  | FindFile String
  | Information String
  | Exit
  deriving (Show)

-- | Optics for Opt. Helper for console
newtype Optics = Optics
  { opticsCommand :: Command
  }
  deriving (Show)