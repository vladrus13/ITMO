module Parser.Simple where

import Parser.Parser
import Parser.Primitive
import Control.Applicative ((<|>))
import Data.Char (isDigit, ord)

-- CBS -> (CBS)CBS
-- CBS -> e

-- | Parse corrent bracket subsequence
correctBracketSubsequence :: Parser Char ()
correctBracketSubsequence = (element '(' *> correctBracketSubsequence <* element ')') *> correctBracketSubsequence <|> ok

-- | Parse correct bracket sequence to end
correctBracketSequence :: Parser Char ()
correctBracketSequence = correctBracketSubsequence *> eof

-- Int -> Sign Number
-- Sign -> "+" | "-" | ""
-- Number -> Digit Nubmer | ""
-- Digit -> [0-9]

-- Why is <$? https://stackoverflow.com/questions/14087881/what-is-the-purpose-of-in-the-functor-class

-- | Parse signum of number
parseSign :: Parser Char ((t, Int) -> Int)
parseSign = (snd <$ element '+') <|> ((negate . snd) <$ element '-') <|> (snd <$ ok)

-- | Parse digit
parseDigit :: Parser Char Int
parseDigit = fmap (\c -> ord c - ord '0') (satisfy isDigit)

-- | Parse number without signum
parseNumber :: Parser Char (Int, Int)
parseNumber = ((insertDigit <$> parseDigit) <*> parseNumber) <|> ((0, 0) <$ ok) where
    insertDigit digit (count, number) = (count + 1, digit * (10 ^ count) + number)

-- | Parse number
parseInt :: Parser Char Int
parseInt = (parseSign <*> parseNumber)

-- | Parse number to end
parseOnlyInt :: Parser Char Int
parseOnlyInt = parseInt <* eof