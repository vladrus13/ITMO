module Command.Exit
  ( run,
    commandM,
  )
where

import Control.Monad.Trans.Class (lift)
import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State)
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
import Types (Command (Exit), FileSystemContainer)

-- | Run exit command
run :: ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
run = lift $ tell "exit" >> pure ()

-- | Parser part for exit
commandM :: Mod CommandFields Command
commandM = command "exit" (info (pure Exit) (progDesc "Exit from console"))