module Main where

import Grammar
import Lexer

main :: IO()
main = do
    expression <- getLine
    putStrLn . show . parseExpression . alexScanTokens $ expression