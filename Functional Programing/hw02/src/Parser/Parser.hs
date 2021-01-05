{-# LANGUAGE InstanceSigs #-}
module Parser.Parser where

import Control.Applicative (Alternative (..))

-- | Parser data
data Parser s a = Parser { runParser :: [s] -> Maybe (a, [s]) }

-- | Functor instance for parser
instance Functor (Parser t) where
    fmap :: (a -> b) -> Parser t a -> Parser t b
    fmap f p = Parser (\d -> do
        (a, k) <- runParser p d
        return (f a, k))

-- | Applicative instance for parser
instance Applicative (Parser t) where
    pure :: a -> Parser t a
    pure a = Parser (\p -> Just (a, p))
    (<*>) :: Parser t (a -> b) -> Parser t a -> Parser t b
    a <*> b = Parser (\p -> do
        (first, firstS) <- runParser a p
        (second, secondS) <- runParser b firstS
        return (first second, secondS))

-- | Monad instance for parser
instance Monad (Parser t) where
    return :: a -> Parser t a
    return = pure
    (>>=) :: Parser t a -> (a -> Parser t b) -> Parser t b
    (>>=) (Parser r) f = Parser (\p -> r p >>= (\(x, r) -> runParser (f x) r))

-- | Alternative instance for parser
instance Alternative (Parser t) where
    empty :: Parser t a
    empty = Parser (const Nothing)
    (<|>) :: Parser t a -> Parser t a -> Parser t a
    (<|>) a b = Parser (\p -> runParser a p <|> runParser b p)