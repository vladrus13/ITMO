module Command.Ls(
    run,
    commandM
) where

import Types (FileInfo(fileName), FileSystemContainer, FileSystem(Folder), FileName(..), FileSystem(File), Command(Ls))
import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.State (State, get)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.Writer (WriterT, tell)
import Errors (Error(UnexpectedFile, DirectoryNotFound, UnexpectedError))
import Data.Time.Clock (UTCTime)
import Utils(fileSystemTo, unlines, toDirectory, throwError)
import Control.Monad (foldM)
import System.FilePath.Posix (splitDirectories)
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

-- | Run ls command with path
run :: String -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run path = do
  lift . lift . lift $ toDirectory

  fz@(directory, _, _) <- lift . lift . lift $ get
  case directory of
    (Folder _ _) -> do
      let result = foldM (flip fileSystemTo) fz (splitDirectories path)
      case result of
        Just (File file, _, _) ->
          throwError $ UnexpectedFile $ fileName file
        Just (Folder _ files, _, _) ->
          lift . tell . Utils.unlines . map (show . FileName) $ files
        Nothing -> throwError $ DirectoryNotFound path
    _ -> throwError UnexpectedError

-- | Parser part for ls
commandM :: Mod CommandFields Command
commandM = command "ls" (info optionsM (progDesc "Show current directory"))

-- | Info part for ls
optionsM :: Parser Command
optionsM = Ls <$> strArgument (metavar "<folder>" <> help "Name of folder to show")