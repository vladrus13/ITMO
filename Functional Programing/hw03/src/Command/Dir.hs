module Command.Dir
  ( run,
    commandM,
  )
where

import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State, get)
import Control.Monad.Trans.Writer (WriterT, tell)
import Data.Time.Clock (UTCTime)
import Errors (Error)
import Options.Applicative
  ( CommandFields,
    Mod,
    command,
    info,
    progDesc,
  )
import Types (Command (Dir), FileName (..), FileSystem (Folder), FileSystemContainer)
import Utils (toDirectory, unlines)

-- | Run directory shower command
run :: ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run = do
  (directory, _, _) <- lift . lift . lift $ get
  case directory of
    (Folder _ xs) -> lift . tell . Utils.unlines . map (show . FileName) $ xs
    _ -> do
      lift . lift . lift $ toDirectory
      run

-- | Parser part for directory shower
commandM :: Mod CommandFields Command
commandM = command "dir" (info (pure Dir) (progDesc "Show current directory"))