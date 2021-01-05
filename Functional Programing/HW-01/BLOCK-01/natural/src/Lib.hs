{-# LANGUAGE InstanceSigs #-}
module Lib where

-- | Natural numbers class
data Nat = Z | S Nat
    deriving (Show)

-- | Instance for natural numbers
instance Num Nat where
    (+) :: Nat -> Nat -> Nat
    (+) a Z = a
    (+) a (S b) = S (a + b)

    (-) :: Nat -> Nat -> Nat
    (-) a Z = a
    (-) Z _ = Z
    (-) (S a) (S b) = a - b

    (*) :: Nat -> Nat -> Nat
    (*) _ Z = Z
    (*) a (S b) = a + (a * b)

    abs :: Nat -> Nat
    abs x = x

    signum :: Nat -> Nat
    signum Z = Z
    signum _ = S (Z)

    fromInteger :: Integer -> Nat
    fromInteger 0 = Z
    fromInteger x
        | x > 0 = S (fromInteger (x - 1))
        | x < 0 = error "Negative integer in fromInteger"

-- | Get from Natural number Integer number
fromNum :: Nat -> Int
fromNum Z = 0
fromNum (S a) = 1 + (fromNum a)

-- | Get from Integer number Natural number
toNum :: Int -> Nat
toNum a
    | a > 0 = S (toNum (a - 1))
    | a == 0 = Z
    | a < 0 = error "Negative int in toNum"

-- | Modular operation 
mod :: Nat -> Nat -> Nat
mod _ Z = error "Modular from zero"
mod a b = a - ((Lib.div a b) * b)

-- | Divide operation
div :: Nat -> Nat -> Nat
div _ Z = error "Divide by zero"
div Z b = Z
div a b = if ((S (a)) - b == Z) then Z else S (Lib.div (a - b) b)

-- | Return True if even number
isEven :: Nat -> Bool
isEven (S (S a)) = isEven a
isEven Z = True
isEven (S Z) = False

-- | Equals instance for Natural number
instance Eq Nat where
    (==) :: Nat -> Nat -> Bool
    (==) Z Z = True
    (==) (S a) (S b) = a == b
    (==) _ _ = False

-- | Ordered instance for Natural number
instance Ord Nat where
    (<=) :: Nat -> Nat -> Bool
    (<=) Z _ = True
    (<=) (S a) (S b) = a <= b
    (<=) (S a) Z = False