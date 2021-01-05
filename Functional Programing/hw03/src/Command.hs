module Command where

import Control.Monad.Trans.Except (ExceptT)
import Control.Monad.Trans.Reader (ReaderT)
import Control.Monad.Trans.State (State)
import Control.Monad.Trans.Writer (WriterT)
import Data.Time.Clock (UTCTime)
import Errors ( Error )
import Types
  ( 
    FileSystemContainer,
    Command(FindFile, Delete, CreateDir, CreateFile, WriteFile, Information, Cat, Ls, Cd, Dir, Exit)
  )
import Command.Exit(run)
import Command.Dir(run)
import Command.Cd(run)
import Command.Ls(run)
import Command.Cat(run)
import Command.Information(run)
import Command.WriteFile(run)
import Command.CreateFile(run)
import Command.CreateDir(run)
import Command.Delete(run)
import Command.FindFile(run)

-- | Run Command with real file system
runrun :: Command -> ReaderT UTCTime (WriterT String (ExceptT Error (State FileSystemContainer))) ()
runrun Exit = Command.Exit.run

runrun Dir = Command.Dir.run

runrun (Cd path) = Command.Cd.run path

runrun (Ls path) = Command.Ls.run path

runrun (Cat path) = Command.Cat.run path

runrun (Information path) = Command.Information.run path

runrun (WriteFile path content) = Command.WriteFile.run path content

runrun (CreateFile name) = Command.CreateFile.run name

runrun (CreateDir name) = Command.CreateDir.run name

runrun (Delete name) = Command.Delete.run name

runrun (FindFile name) = Command.FindFile.run name