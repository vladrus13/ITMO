{-# LANGUAGE InstanceSigs #-}
module Tree where

import Control.Applicative (liftA2)

-- | Tree data
data Tree a
  = Branch (Tree a) (Tree a)
  | Leaf a

-- | Functor instance for Tree
instance Functor Tree where
  fmap :: (a -> b) -> Tree a -> Tree b
  fmap f (Branch left right) = Branch (fmap f left) (fmap f right)
  fmap f (Leaf a) = Leaf (f a)

-- | Applicative instance for Tree
instance Applicative Tree where
  pure :: a -> Tree a
  pure = Leaf
  (<*>) :: Tree (a -> b) -> Tree a -> Tree b
  (<*>) (Branch left right) x = Branch (left <*> x) (right <*> x)
  (<*>) (Leaf a) x = a <$> x

-- | Foldable instance for Tree
instance Foldable Tree where
  foldMap :: (Monoid a) => (b -> a) -> Tree b -> a
  foldr :: (a -> b -> b) -> b -> Tree a -> b
  foldMap f (Branch left right) = foldMap f left <> foldMap f right
  foldMap f (Leaf a) = f a
  foldr f x (Branch left right) = foldr f (foldr f x left) right

-- | Traversable instance for Tree
instance Traversable Tree where
  traverse :: Applicative f => (a -> f b) -> Tree a -> f (Tree b)
  traverse f (Branch left right) = liftA2 Branch (traverse f left) (traverse f right)
  traverse f (Leaf a) = Leaf <$> f a;