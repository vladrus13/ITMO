module Optics (
  programOptions
) where

import Options.Applicative
  ( CommandFields,
    Mod,
    Parser,
    hsubparser
  )
import Types(Optics(..), Command)
import Command.Cd(commandM)
import Command.Dir(commandM)
import Command.Exit(commandM)
import Command.Ls(commandM)
import Command.CreateDir(commandM)
import Command.Cat(commandM)
import Command.CreateFile(commandM)
import Command.Delete(commandM)
import Command.WriteFile(commandM)
import Command.FindFile(commandM)
import Command.Information(commandM)

-- | Optics for Opt to help with console
programOptions :: Parser Optics
programOptions =
  Optics
    <$> hsubparser
      ( cdCommand
          <> dirCommand
          <> lsCommand
          <> createDirCommand
          <> catCommand
          <> createFileCommand
          <> deleteCommand
          <> writeFileCommand
          <> findFileCommand
          <> informationCommand
          <> exitCommand
      )

-- | Cd part command
cdCommand :: Mod CommandFields Command
cdCommand = Command.Cd.commandM

-- | Dir part command
dirCommand :: Mod CommandFields Command
dirCommand = Command.Dir.commandM

-- | Exit part command
exitCommand :: Mod CommandFields Command
exitCommand = Command.Exit.commandM

-- | Ls part command
lsCommand :: Mod CommandFields Command
lsCommand = Command.Ls.commandM

-- | Create dir command
createDirCommand :: Mod CommandFields Command
createDirCommand = Command.CreateDir.commandM

-- | Cat command
catCommand :: Mod CommandFields Command
catCommand = Command.Cat.commandM

-- | Create file command
createFileCommand :: Mod CommandFields Command
createFileCommand = Command.CreateFile.commandM

-- | Delete command
deleteCommand :: Mod CommandFields Command
deleteCommand = Command.Delete.commandM

-- | Write file command
writeFileCommand :: Mod CommandFields Command
writeFileCommand = Command.WriteFile.commandM

-- | Find file command
findFileCommand :: Mod CommandFields Command
findFileCommand = Command.FindFile.commandM

-- | Information command
informationCommand :: Mod CommandFields Command
informationCommand = Command.Information.commandM