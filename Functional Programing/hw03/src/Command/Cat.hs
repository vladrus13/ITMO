module Command.Cat
  ( run,
    commandM,
  )
where

import Control.Monad (foldM)
import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State, get)
import Control.Monad.Trans.Writer (WriterT, tell)
import Data.Time.Clock (UTCTime)
import Errors (Error (FileNotFound, UnexpectedDirectory, UnexpectedError))
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
import Types (Command (Cat), DirectoryInfo (directoryName), FileInfo (fileContent), FileSystem (File, Folder), FileSystemContainer)
import Utils (fileSystemTo, throwError, toDirectory)

-- | Run cat command with path
run :: String -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run path = do
  lift . lift . lift $ toDirectory

  fz@(fss, _, _) <- lift . lift . lift $ get
  case fss of
    (Folder _ _) -> do
      let res = foldM (flip fileSystemTo) fz (splitDirectories path)
      case res of
        Just (File file, _, _) -> lift . tell $ fileContent file
        Just (Folder directory _, _, _) ->
          throwError $ UnexpectedDirectory $ directoryName directory
        Nothing -> throwError $ FileNotFound path
    _ -> throwError UnexpectedError

-- | Parser part for cat
commandM :: Mod CommandFields Command
commandM = command "cat" (info optionsM (progDesc "Show content of file"))

-- | Info part for cat
optionsM :: Parser Command
optionsM = Cat <$> strArgument (metavar "<file>" <> help "Name of file to show")