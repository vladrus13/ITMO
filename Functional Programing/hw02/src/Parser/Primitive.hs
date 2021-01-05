module Parser.Primitive where

import Parser.Parser

-- If I say token, I mean token-combinator

-- | ok-token for parser. Parser not fail and not read data
ok :: Parser t ()
ok = Parser(\ignored -> Just((), ignored))

-- | eof-token for parser. Parser check to end of "file"
eof :: Parser t ()
eof = Parser(\case
    [] -> Just ((), [])
    _ -> Nothing)

-- | satisfy-token for parser. Satisfy for predicate
satisfy :: (t -> Bool) -> Parser t t
satisfy p = Parser (\case
    [] -> Nothing
    (x : xs) -> 
        if p x 
        then Just (x, xs) 
        else Nothing)

-- | Element-token for parser. Parse one element
element :: (Eq t) => t -> Parser t t
element char = satisfy (char ==)

-- | Stream-token for parser. Parse string
stream :: (Eq t) => [t] -> Parser t [t]
stream = traverse element