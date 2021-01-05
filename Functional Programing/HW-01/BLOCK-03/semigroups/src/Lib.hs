{-# LANGUAGE InstanceSigs #-}
module Lib where

-- | NonEmpty structure
data NonEmpty a = a :| [a] 
    deriving (Show)

-- | Structure with That or That or Both element
data ThisOrThat a b = This a | That b | Both a b
    deriving (Show)

-- | Semigroup instance for NonEmpty
instance Semigroup (NonEmpty a) where
    (<>) :: (NonEmpty a) -> (NonEmpty a) -> (NonEmpty a)
    (<>) (a :| as) (b :| bs) = a :| (as ++ b : bs)

-- | Equals instance for NonEmpty
instance (Eq a) => Eq (NonEmpty a) where
    (==) :: (NonEmpty a) -> (NonEmpty a) -> Bool
    (==) (a :| as) (b :| bs) = (a == b) && (as == bs)

-- | Semigroup instance for ThatOrThat
instance (Semigroup a, Semigroup b) => Semigroup (ThisOrThat a b) where
    (<>) :: (ThisOrThat a b) -> (ThisOrThat a b) -> (ThisOrThat a b)
    (<>) (This a) (This b) = This (a <> b)
    (<>) (This a) (That b) = Both a b
    (<>) (This a) (Both b c) = Both (a <> b) c
    (<>) (That a) (This b) = Both b a
    (<>) (That a) (That b) = That (a <> b)
    (<>) (That a) (Both b c) = Both b (a <> c)
    (<>) (Both a b) (This c) = Both (a <> c) b
    (<>) (Both a b) (That c) = Both a (b <> c)
    (<>) (Both a b) (Both c d) = Both (a <> c) (b <> d)

-- | Equals instance for ThisOrThat
instance (Eq a, Eq b) => Eq (ThisOrThat a b) where
    (==) :: (ThisOrThat a b) -> (ThisOrThat a b) -> Bool
    (==) (This a) (This b) = a == b
    (==) (That a) (That b) = a == b
    (==) (Both a b) (Both c d) = (a == c) && (b == d)
    (==) _ _ = False

-- ADVANCED --

-- | Class with name
newtype Name = Name String
    deriving (Show)

-- | Endo type
newtype Endo a = Endo { getEndo :: a -> a }

-- | Monoid with neutral element
instance Monoid Name where
    mempty :: Name
    mempty = Name ""

-- | Semigroup instance for Name
instance Semigroup Name where
    (<>) :: Name -> Name -> Name
    (<>) (Name "") a = a
    (<>) a (Name "") = a
    (<>) (Name a) (Name b) = Name (a ++ ('.' : b))

-- | Equals instance for Name
instance Eq Name where
    (==) :: Name -> Name -> Bool
    (==) (Name a) (Name b) = a == b

-- | Monoid instance for Endo with neutral element
instance Monoid (Endo a) where
    mempty :: Endo a
    mempty = Endo id

-- | Semigroup instance for Endo
instance Semigroup (Endo a) where
    (<>) :: (Endo a) -> (Endo a) -> (Endo a)
    (<>) (Endo a) (Endo b) = Endo (a . b)