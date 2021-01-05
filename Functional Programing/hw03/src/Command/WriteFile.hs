module Command.WriteFile
  ( run,
    commandM
  )
where

import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State, get, put)
import Control.Monad.Trans.Writer (WriterT)
import Data.Time.Clock (UTCTime)
import Errors (Error (FileNotFound, UnexpectedDirectory, UnexpectedError))
import System.FilePath.Posix (takeFileName)
import Types (DirectoryInfo (directoryName), FileSystem (File, Folder), FileSystemContainer, fileContent, Command(WriteFile))
import Utils (fileSystemTo, throwError, toDirectory)
import Control.Applicative (many)
import Options.Applicative
  ( CommandFields,
    Mod,
    Parser,
    command,
    help,
    info,
    metavar,
    progDesc,
    strArgument
  )

-- | Run write to file with path and content
run :: String -> [String] -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run path content = do
  lift . lift . lift $ toDirectory
  fz@(file, _, _) <- lift . lift . lift $ get
  case file of
    (Folder _ _) -> case fileSystemTo (takeFileName path) fz of
      Just (File file', bs, flags) ->
        lift . lift . lift $ put (File file' {fileContent = unwords content}, bs, flags)
      Just (Folder directory _, _, _) ->
        throwError $ UnexpectedDirectory $ directoryName directory
      Nothing ->
        throwError $ FileNotFound path
    _ -> throwError UnexpectedError

-- | Parser part for write file
commandM :: Mod CommandFields Command
commandM = command "write-file" (info optionsM (progDesc "Write text to file"))

-- | Info part for write file
optionsM :: Parser Command
optionsM =
  WriteFile
    <$> strArgument (metavar "<name>" <> help "Name of file to write")
    <*> many (strArgument (metavar "<text>" <> help "Test to write"))