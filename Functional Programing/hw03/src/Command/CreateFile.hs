module Command.CreateFile
  ( run,
    commandM,
    optionsM,
  )
where

import Control.Monad (when)
import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT, ask)
import Control.Monad.Trans.State (State, get, put)
import Control.Monad.Trans.Writer (WriterT)
import Data.Time.Clock (UTCTime)
import Errors (Error (Another, FileAlreadyExists, UnexpectedError))
import Options.Applicative
  ( CommandFields,
    Mod,
    Parser,
    command,
    help,
    info,
    metavar,
    progDesc,
    strArgument,
  )
import System.Directory.Internal (Permissions (Permissions))
import System.FilePath.Posix ((</>))
import Types (Command (CreateFile), FileInfo (..), FileSystem (File), FileSystemContainer)
import Utils (fileSystemNewFile, fileSystemTo, getDirectoryFullPath, throwError, toDirectory)

-- | Run create file command with name
run :: String -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run name = do
  when ('/' `elem` name) (throwError $ Another $ "Bad character '/' on name of file " ++ name)
  lift . lift . lift $ toDirectory
  currentTime <- ask
  fz <- lift . lift . lift $ get
  let newFile =
        File
          FileInfo
            { fileName = name,
              filePath = getDirectoryFullPath fz </> name,
              fileContent = "",
              filePermission = Permissions True True False False,
              fileSize = 0,
              fileTime = currentTime
            }
  case fileSystemTo name fz of
    Nothing -> case fileSystemNewFile newFile fz of
      Just fz' -> lift . lift . lift $ put fz'
      Nothing -> throwError UnexpectedError
    Just _ -> throwError $ FileAlreadyExists name

-- | Parser part for create file
commandM :: Mod CommandFields Command
commandM = command "create-file" (info optionsM (progDesc "Create file"))

-- | Info part for create file
optionsM :: Parser Command
optionsM = CreateFile <$> strArgument (metavar "<file>" <> help "Name of file to create")