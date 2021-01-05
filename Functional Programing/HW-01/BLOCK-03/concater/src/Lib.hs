module Lib where

import Data.Either (lefts, rights)
import Data.Maybe (catMaybes)

-- | Concat all Maybe's to array
maybeConcat :: [Maybe [a]] -> [a]
maybeConcat x = (foldr (<>) [] (catMaybes x))

-- | Concat all Either's to array
eitherConcat :: (Monoid a, Monoid b) => [Either a b] -> (a, b)
eitherConcat x = ((mconcat (lefts x)), (mconcat (rights x)))