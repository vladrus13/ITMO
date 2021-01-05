{-# LANGUAGE InstanceSigs #-}
module NonEmpty where

import Control.Applicative (liftA2)

-- | NonEmpty data
data NonEmpty a = a :| [a]

-- | Append a list to a non-empty list
appendl :: NonEmpty a -> [a] -> NonEmpty a
appendl (x :| xs) l = x :| (xs ++ l)

-- | Make list from NonEmpty
toList :: NonEmpty a -> [a]
toList (a :| as) = a : as

-- | Functor instance for NonEmpty
instance Functor NonEmpty where
  fmap :: (a -> b) -> NonEmpty a -> NonEmpty b
  fmap f (x :| xs) = f x :| fmap f xs

-- | Applicative instance for NonEmpty
instance Applicative NonEmpty where
  pure :: a -> NonEmpty a
  pure = ( :| [])
  (<*>) :: NonEmpty (a -> b) -> NonEmpty a -> NonEmpty b
  (<*>) (f :| fs) (x :| xs) = (f x) :| ((fs <*> xs) ++ (fs <*> (x : xs)))

-- | Monad instance for NonEmpty
instance Monad NonEmpty where
  (>>=) :: NonEmpty a -> (a -> NonEmpty b) -> NonEmpty b
  (>>=) (x :| xs) f = appendl (f x) (xs >>= toList . f)  

-- | Foldable instance for NonEmpty
instance Foldable NonEmpty where
  foldMap :: (Monoid a) => (b -> a) -> NonEmpty b -> a
  foldr :: (a -> b -> b) -> b -> NonEmpty a -> b
  foldMap f (x :| xs) = (f x) <> (foldMap f xs)
  foldr f a (x :| xs) = f x (foldr f a xs) 

-- | Traversable instance for NonEmpty
instance Traversable NonEmpty where
  traverse :: Applicative f => (a -> f b) -> NonEmpty a -> f (NonEmpty b)
  traverse f (x :| xs) = liftA2 (:|) (f x) (traverse f xs)