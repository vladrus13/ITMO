{-# LANGUAGE InstanceSigs #-}

module Lib where

-- OLD
import qualified Data.List.NonEmpty as NE

-- | Tree structure
data Tree a = Nil | Node (NE.NonEmpty a) (Tree a) (Tree a)
    deriving (Show)

-- | Equals instance for Tree
instance (Eq a) => Eq (Tree a) where
    (==) :: Tree a -> Tree a -> Bool
    (==) Nil Nil = True
    (==) (Node (a NE.:| as) b c) (Node (a' NE.:| a's) b' c') = (a == a') && (b == b') && (c == c')
    (==) _ _ = False

-- | Check is empty
isEmpty :: Tree a -> Bool
-- | Return size of tree
size :: Tree a -> Int
-- | Find a subtree of tree with root with number
find :: Ord a => Tree a -> a -> Maybe (Tree a)
-- | Insert to Tree Ordered number
insert :: Ord a => Tree a -> a -> Tree a
-- | Convert to list
toList :: Ord a => Tree a -> [a]
-- | Convert from List
fromList :: Ord a => [a] -> Tree a
-- | Remove from Tree Ordered number
remove :: Ord a => Tree a -> a -> Tree a

isEmpty Nil = True
isEmpty (Node _ _ _) = False

size Nil = 0
size (Node a left right) = (length a) + (size left) + (size right)

find Nil x = Nothing
find t@(Node (k NE.:| _) left right) x
    | x == k =      Just t
    | x > k =       find right x
    | otherwise =   find left x

insert Nil x = Node (x NE.:| []) Nil Nil
insert (Node nd@(k NE.:| _) l r) x
    | x == k = Node (x NE.<| nd) l r
    | x > k = Node nd l (insert r x)
    | otherwise = Node nd (insert l x) r

toList Nil = []
toList (Node x l r) = (toList l) ++ (NE.toList x) ++ (toList r)

-- | Insert all Ordered to Tree
insertAll :: Ord a => Tree a -> [a] -> Tree a
insertAll tree [] = tree
insertAll tree a = insertAll (insert tree (head a)) (tail a) 

fromList [] = Nil
fromList a = insertAll Nil a

remove Nil x = Nil
remove nd@(Node ls@(k NE.:| ks) l r) x
    | x == k = case ks of
        [] -> superRemove nd
        kk : kks -> Node (kk NE.:| kks) l r
    | x > k = Node ls l (remove r x)
    | otherwise = Node ls (remove l x) r where
        superRemove (Node _ left right) =
            case leftGet right of
                Nil -> left
                Node superKey _ superR -> Node superKey left superR
        leftGet (Node xx left right) = 
            case leftGet left of
                Nil -> Node xx left right
                Node superK _ superR -> Node superK left (Node xx superR right)
        leftGet Nil = Nil

-- | Turn Tree to String
toString Nil = ""
toString (Node k l r) = "(" ++ toString l ++ " |" ++ show (NE.toList k) ++ ", " ++ (show (length k)) ++ "| " ++ toString r ++ ")"

-- OLD

-- | Foldable instance for Tree
instance Foldable Tree where
    foldr :: (a -> b -> b) -> b -> (Tree a) -> b
    foldr f x Nil = x
    foldr f x (Node keys left right) = foldr f (foldr f (foldr f x right) keys) left

    foldMap :: (Monoid m) => (a -> m) -> Tree a -> m
    foldMap f Nil = mempty
    foldMap f (Node keys left right) = foldMap f left `mappend` foldMap f keys `mappend` foldMap f right