module Main where

import Lib

main :: IO ()
main = putStrLn (toString (fromList [1, 2, 4, 3, 5, 6]))
--main = putStrLn "Finally... Main launch"