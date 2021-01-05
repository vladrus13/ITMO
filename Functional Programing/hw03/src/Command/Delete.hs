module Command.Delete
  ( run,
    commandM,
  )
where

import Control.Monad (when)
import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State, get, put)
import Control.Monad.Trans.Writer (WriterT)
import Data.Time.Clock (UTCTime)
import Errors (Error (Another, FileNotFound))
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
import Types (Command (Delete), FileSystemContainer)
import Utils (fileSystemDeleteFile, throwError, toDirectory)

-- | Run delete command with name
run :: String -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run name = do
  when ('/' `elem` name) (throwError $ Another $ "Bad character '/' on name of file " ++ name)
  lift . lift . lift $ toDirectory
  fz <- lift . lift . lift $ get
  case fileSystemDeleteFile name fz of
    Nothing ->
      throwError $ FileNotFound name
    Just fz' -> lift . lift . lift $ put fz'

-- | Parser part for delete
commandM :: Mod CommandFields Command
commandM = command "remove" (info optionsM (progDesc "Remove file or directory"))

-- | Info part for delete
optionsM :: Parser Command
optionsM = Delete <$> strArgument (metavar "<name>" <> help "Name of file or directory to remove")