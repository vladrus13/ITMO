module Main where

import Control.Monad (foldM)
import System.Random (newStdGen, random)
import Comonad.Comonad

main :: IO ()
main = do
    putStrLn "Hello! This is comonad-19 simulation!"
    putStrLn "Enter please grid output size: "
    size <- read <$> getLine
    putStrLn "Max tics: "
    ticks <- read <$> getLine
    putStrLn "Probability: "
    probability <- read <$> getLine
    putStrLn "Incubation period: "
    incubation <- read <$> getLine
    putStrLn "Illness period: "
    illness <- read <$> getLine
    putStrLn "Immutity period: "
    immunity <- read <$> getLine
    firstGen <- newStdGen
    let (first, _) = random firstGen
    ready <- foldM (
        \grid tick' -> do
            putStrLn ("Day: " ++ show tick')
            putStrLn (showWithInt grid size)
            return (tick probability incubation illness immunity grid)
        ) (makeField first) ([1..ticks] :: [Int])
    putStrLn "Final day"
    putStrLn (showWithInt ready size)