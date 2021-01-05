module Command.Cd
  ( run,
    commandM,
  )
where

import Control.Monad (foldM)
import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State, get, put)
import Control.Monad.Trans.Writer (WriterT)
import Data.Time.Clock (UTCTime)
import Errors (Error (DirectoryNotFound, UnexpectedError, UnexpectedFile))
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
import Types (Command (Cd), FileInfo (fileName), FileSystem (File, Folder), FileSystemContainer)
import Utils (fileSystemTo, throwError, toDirectory)

-- | Run cd command with path
run :: String -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run path = do
  lift . lift . lift $ toDirectory

  fz@(fss, _, _) <- lift . lift . lift $ get

  case fss of
    (Folder _ _) -> do
      let result = foldM (flip fileSystemTo) fz (splitDirectories path)
      case result of
        Just (File file, _, _) ->
          throwError $ UnexpectedFile (fileName file)
        Just newFZ -> lift . lift . lift $ put newFZ
        Nothing -> throwError $ DirectoryNotFound path
    _ -> throwError UnexpectedError

-- | Parser part for cd
commandM :: Mod CommandFields Command
commandM = command "cd" (info optionsM (progDesc "Go to directory"))

-- | Info part for cd
optionsM :: Parser Command
optionsM = Cd <$> strArgument (metavar "<folder>" <> help "Folder name")