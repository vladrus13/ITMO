module StringSum where

import Text.Read (readMaybe)

-- | Read one or more integers and return Just sum. If we can't parse some - return Nothing
stringSum :: String -> Maybe Int
stringSum s = sum <$> (traverse readMaybe (words s))