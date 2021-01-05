module Lib where

import Data.List.NonEmpty

-- | Split by symbols to non empty array
splitOn :: (Eq a) => a -> [a] -> NonEmpty [a]
splitOn s = foldr splitted ([] :| []) where
    splitted current (x :| xs) = 
        if (current == s) then [] :| (x : xs) else (current : x) :| xs

-- | Join by symbol
joinWith :: a -> NonEmpty [a] -> [a]
joinWith s (x :| xs) = x ++ foldr (\first second -> s : (first ++ second)) [] xs