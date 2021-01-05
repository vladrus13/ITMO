module Parser.Hard where

import Prelude
import Parser.Parser
import Parser.Primitive
import Parser.Simple
import Data.Char (isSpace)
import Control.Applicative ((<|>), many, liftA2)
import Control.Monad (replicateM)

-- | Skip spaces
skipSpaces :: Parser Char ()
skipSpaces = (satisfy isSpace *> skipSpaces) <|> ok

-- | Skip spaces, one comma, spaces again
skipDelimiter :: Parser Char Char
skipDelimiter = skipSpaces *> element ',' <* skipSpaces

-- | Parse list with fix length
parseFixedList :: Int -> Parser Char [Int]
parseFixedList length = replicateM length (skipDelimiter *> parseInt)

-- | Parse list with non fixing length (first is length)
parseList :: Parser Char [Int]
parseList = parseInt >>= parseFixedList

-- | Parse list of lists (@link hw)
listlistParser :: Parser Char [[Int]]
listlistParser = skipSpaces *> ((eof *> Parser (const (Just ([], "")))) 
    <|> liftA2 (:) (parseList) (many (skipDelimiter *> (parseList))) <* eof)