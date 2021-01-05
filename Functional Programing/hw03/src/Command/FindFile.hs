module Command.FindFile
  ( run,
    commandM
  )
where

import Control.Monad (when)
import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State, get)
import Control.Monad.Trans.Writer (WriterT, tell)
import Data.Time.Clock (UTCTime)
import Errors (Error(Another, UnexpectedError))
import Types (FileSystem (Folder), FileSystemContainer, FileSystemPath(..), Command(FindFile))
import Utils (walker, throwError, toDirectory, unlines)
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

-- | Run find file command with name
run :: String -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run name = do
  when ('/' `elem` name) (throwError $ Another $ "Bad character '/' on name of file " ++ name)
  lift . lift . lift $ toDirectory
  (file, _, _) <- lift . lift . lift $ get
  case file of
    (Folder _ files') -> do
      let files = walker name files'
      lift $ tell (Utils.unlines (map (show . FileSystemPath) files))
    _ -> throwError UnexpectedError

-- | Parser part for find file
commandM :: Mod CommandFields Command
commandM = command "find-file" (info optionsM (progDesc "Find file in current directory or deeper"))

-- | Info part for find file
optionsM :: Parser Command
optionsM = FindFile <$> strArgument (metavar "<name>" <> help "Name of file or directory to find")