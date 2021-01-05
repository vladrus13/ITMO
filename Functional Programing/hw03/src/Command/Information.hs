module Command.Information
  ( run,
    commandM,
    optionsM,
  )
where

import Control.Monad (foldM)
import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State, get)
import Control.Monad.Trans.Writer (WriterT, tell)
import Data.Time.Clock (UTCTime)
import Errors (Error (FileNotFound, UnexpectedError))
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
import System.FilePath.Posix (splitDirectories)
import Types (Command (Information), FileSystem (Folder), FileSystemContainer, FileSystemInfo (..))
import Utils (fileSystemTo, throwError, toDirectory)

-- | Run information command with path
run :: String -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run path = do
  lift . lift . lift $ toDirectory
  fz@(fd, _, _) <- lift . lift . lift $ get
  case fd of
    (Folder _ _) -> do
      let res = foldM (flip fileSystemTo) fz (splitDirectories path)
      case res of
        Just (f, _, _) -> lift . tell $ show $ FileSystemInfo f
        Nothing -> throwError $ FileNotFound path
    _ -> throwError UnexpectedError

-- | Parser part for information
commandM :: Mod CommandFields Command
commandM = command "information" (info optionsM (progDesc "Show information about directory of file"))

-- | Info part for information
optionsM :: Parser Command
optionsM = Information <$> strArgument (metavar "<name>" <> help "Name of file or directory to show")