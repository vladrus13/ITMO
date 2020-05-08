module Main where
import Control.Applicative ((<$>))

main :: IO ()
main = (*) <$> getInt <*> getInt >>= print
    where getInt = readLn::IO Integer
