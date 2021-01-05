{-# LANGUAGE ScopedTypeVariables #-}

module Main where

import Command
import Control.Exception (IOException, catch)
import Control.Monad (unless, when)
import Control.Monad.IO.Class (liftIO)
import Control.Monad.Trans.Except (runExceptT)
import Control.Monad.Trans.Reader (runReaderT)
import Control.Monad.Trans.State (StateT, execStateT, get, put, runState)
import Control.Monad.Trans.Writer (runWriterT)
import Data.Time.Clock (UTCTime, getCurrentTime)
import Errors
import FileSystem (read, save)
import Optics
import Options.Applicative
  ( ParserFailure (..),
    ParserResult (..),
    defaultPrefs,
    execParserPure,
    fullDesc,
    helper,
    info,
  )
import System.Environment (getArgs)
import System.IO (hFlush, stdout)
import Types (FileSystemContainer, Flags (..), Optics (opticsCommand))
import Utils (getDirectoryFullPath, isImmediately)

-- | Main class
-- On arguments you can give flag 'not_immeditely' for not immiditely File System
main :: IO ()
main = do
  args <- getArgs
  case args of
    [dirPath] -> do
      print ("Run with path: " ++ show dirPath)
      run [] dirPath
    flags : [dirPath] -> do
      print ("Run with path: " ++ show dirPath ++ " and flags: " ++ show flags)
      run flags dirPath
    _ -> putStrLn "Wrong number of arguments"

-- | Run Real File System with flags and path
run :: String -> FilePath -> IO ()
run flag dirPath = do
  fs' <- FileSystem.read flag dirPath `catch` (\(ex :: IOException) -> print ex >> pure Nothing)
  case fs' of
    Nothing -> return ()
    Just fs -> do
      (fs'', _, flags) <- execStateT invoke (fs, [], Flags [flag])
      unless (Utils.isImmediately flags) (liftIO (save fs'' `catch` (\(ex :: IOException) -> print ex >> pure ())))
      putStrLn "Goodbye!"

-- | Parse command
parseCommand :: String -> ParserResult Optics
parseCommand = execParserPure defaultPrefs (info (helper <*> programOptions) fullDesc) . words

-- | Invoke one command from command line
invoke :: StateT FileSystemContainer IO ()
invoke = do
  fsZipper <- get
  liftIO . putStr $ getDirectoryFullPath fsZipper ++ " > "
  liftIO $ hFlush stdout
  cmd <- liftIO getLine
  curTime <- liftIO getCurrentTime
  case parseCommand cmd of
    Success opt -> do
      let (i, newSt) = runner opt curTime fsZipper
      let (fs, _, flags) = newSt
      case i of
        (Left i') -> do
          liftIO (print i')
          invoke
        (Right (_, "exit")) -> pure ()
        (Right (_, res)) -> do
          put newSt
          liftIO (putStrLn (parseResult res))
          when (Utils.isImmediately flags) (liftIO (save fs `catch` (\(ex :: IOException) -> print ex >> pure ())))
          invoke
    Failure (ParserFailure err) -> do
      let (h, _, _) = err ""
      liftIO $ print h
      invoke
    CompletionInvoked _ -> do
      liftIO $
        putStrLn "Can't work with completion invoker"
      invoke

-- | Run command with File System
runner :: Optics -> UTCTime -> FileSystemContainer -> (Either Errors.Error ((), String), FileSystemContainer)
runner cmd time = runState (runExceptT . runWriterT $ runReaderT (runrun . opticsCommand $ cmd) time)

-- | Parse result of command
parseResult :: String -> String
parseResult res
  | Prelude.null res = "Successfully done!"
  | otherwise = res