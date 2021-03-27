{-# LANGUAGE InstanceSigs #-}

-- | Comonad-19 hw
module Comonad.Comonad where

import Control.Comonad (Comonad, duplicate, extend, extract)
import System.Random (StdGen, mkStdGen, random, randoms)
import Data.List (intercalate)

-- | ListZipper
--
-- Taken from presentation
data ListZipper a = LZ [a] a [a]

-- | Go to left or right
--
-- Taken from presentation
listLeft, listRight :: ListZipper a -> ListZipper a
listLeft  (LZ (a:as) x bs) = LZ as a (x:bs)
listLeft _ = error "listLeft"

listRight (LZ as x (b:bs)) = LZ (x:as) b bs
listRight _ = error "listRight"

-- | Write to list
--
-- Taken from presentation
listWrite :: a -> ListZipper a -> ListZipper a
listWrite x (LZ ls _ rs) = LZ ls x rs

-- | ListZipper to List
--
-- Taken from presentation
toList :: ListZipper a -> Int -> [a]
toList (LZ ls x rs) n = reverse (take n ls) ++ [x] ++ take n rs

-- | Instance Function ListZipper
--
-- Taken from presentation
instance Functor ListZipper where
    fmap f (LZ ls x rs) = LZ (map f ls) (f x) (map f rs)

-- | Take tail
--
-- Taken from presentation
iterateTail :: (a -> a) -> a -> [a]
iterateTail f = tail . iterate f

-- | Make move
--
-- Taken from presentation
genericMove :: (z a -> z a) -> (z a -> z a) -> z a -> ListZipper (z a)
genericMove f g e = LZ (iterateTail f e) e (iterateTail g e)

-- | Instance Comonad ListZipper
--
-- Taken from presentation
instance Comonad ListZipper where
    extract :: ListZipper a -> a
    extract (LZ _ x _) = x

    duplicate :: ListZipper a -> ListZipper (ListZipper a)
    duplicate = genericMove listLeft listRight

-- | 2D Grid
--
-- Taken from presentation
newtype Grid a = Grid { unGrid :: ListZipper (ListZipper a) }

-- | Go to up or down
--
-- Taken from presentation
up, down :: Grid a -> Grid a
up   (Grid g) = Grid (listLeft  g)
down (Grid g) = Grid (listRight g)  

-- | Go to left or right
--
-- Taken from presentation
left, right :: Grid a -> Grid a
left  (Grid g) = Grid (fmap listLeft  g)
right (Grid g) = Grid (fmap listRight g)

-- | Read value on grid
--
-- Taken from presentation
gridRead :: Grid a -> a
gridRead (Grid g) = extract $ extract g
 
-- | Write value on grid
--
-- Taken from presentation
gridWrite :: a -> Grid a -> Grid a
gridWrite x (Grid g) = Grid $ listWrite newLine g
  where
    oldLine = extract g
    newLine = listWrite x oldLine

-- | Grid to list
--
-- Taken from presentation
toListG :: Grid a -> Int -> [[a]]
toListG (Grid l) n = toList (fmap (`toList` n) l) n

-- | Take vertical list and horizontal
--
-- Taken from presentation
horizontal, vertical :: Grid a -> ListZipper (Grid a)
horizontal = genericMove left right
vertical   = genericMove up   down

-- | Instance Function of Grid
--
-- Taken from presentation
instance Functor Grid where
    fmap :: (a -> b) -> Grid a -> Grid b
    fmap f (Grid g) = Grid $ fmap (fmap f) g

-- | Instance Comonad of Grid
--
-- Taken from presentation
instance Comonad Grid where
    extract :: Grid a -> a
    extract = gridRead

    duplicate :: Grid a -> Grid (Grid a)
    duplicate = Grid . fmap horizontal . vertical

-- | Type of people
data Type = Infected | Immune | Healthy | Ill

-- | Person data
data Person = Person 
    { type' :: Type
    , count :: Int
    , get :: StdGen
    }

-- | Instance Show of Persion
instance Show Person where
    show (Person Infected _ _ ) = "I"
    show (Person Immune _ _ ) = "@"
    show (Person Healthy _ _ ) = " "
    show (Person Ill _ _ ) = "#"

-- | Show Grid
--
-- Why not instance Show? Because size
showWithInt :: Grid Person -> Int -> String 
showWithInt grid width = concat (intercalate ["\n"] (toListG (fmap show grid) width))

-- | Make full healthy person
makeStartPerson :: Int -> Person
makeStartPerson seed = Person Healthy 0 (mkStdGen seed)

-- | Make ListZipper of random values
makeListOfInt :: Int -> ListZipper Int
makeListOfInt row =
    LZ (map (\x -> 2 * x + 1) next) start next where
        gen = mkStdGen row
        start : next = randoms gen

-- | Generate random Grid of Int
randomIntGrid :: Int -> Grid Int
randomIntGrid = Grid . fmap makeListOfInt . makeListOfInt

-- | Generate random Grid of Person
randomPersonGrid :: Int -> Grid Person
randomPersonGrid seed = makeStartPerson <$> randomIntGrid seed

-- | Make start field
makeField :: Int -> Grid Person
makeField x = makeInfected (randomPersonGrid x)

-- | Make "null pacient"
makeInfected :: Grid Person -> Grid Person
makeInfected grid =
    gridWrite (Person {type' = Infected, count = count', get = get'}) grid where
        Person _ count' get' = extract grid

-- | Take neighbours
neighbours :: [Grid a -> Grid a]
neighbours = horizontals ++ verticals
  where horizontals = [left, right]
        verticals   = [up, down]

-- | Is Person cough
isCough :: Person -> Bool 
isCough (Person Ill _ _) = True 
isCough (Person Infected _ _) = True 
isCough _ = False  

-- | Count of "cough" from neighbours
countCoughs :: [Person] -> Int 
countCoughs = length . filter isCough

-- | Count of "cough" contacts
contacts :: Grid Person -> Int 
contacts grid = countCoughs (map (\i -> extract $ i grid) neighbours)

-- | Simulating contact with person
oneContact :: Double -> StdGen -> Int -> (Bool, StdGen)
oneContact probability gen count' =
    if count' == 0 then
        (False, gen)
    else 
        (lastResults || dice < probability, newGen) where
            (dice, notRealNewGen) = random gen
            (lastResults, newGen) = oneContact probability notRealNewGen (count' - 1)

-- | Tick for one person with returning person
personTick :: Double -> Int -> Int -> Int -> Person -> Grid Person -> Person
personTick _ _ illDay _ (Person Ill count' gen) _ =
    if count' == illDay then
        Person Immune 0 gen
    else 
        Person Ill (count' + 1) gen

personTick _ incubateDay _ _ (Person Infected count' gen) _ =
    if count' == incubateDay then
        Person Ill 0 gen
    else 
        Person Infected (count' + 1) gen

personTick _ _ _ immuneDay (Person Immune count' gen) _ =
    if count' == immuneDay then
        Person Healthy 0 gen
    else 
        Person Immune (count' + 1) gen

personTick probability _ _ _ (Person Healthy _ gen) grid =
    let (bad, newGen) = oneContact probability gen (contacts grid) in
    if bad then 
        Person Infected 0 newGen 
    else 
        Person Healthy 0 newGen

-- | Tick for one person and write it to Grid
tickOne :: Double -> Int -> Int -> Int -> Grid Person -> Person
tickOne probability incubateDay illDay immuneDay grid = personTick probability incubateDay illDay immuneDay (extract grid) grid

-- | Tick for all person
tick :: Double -> Int -> Int -> Int -> Grid Person -> Grid Person
tick probability incubateDay illDay immuneDay = extend (tickOne probability incubateDay illDay immuneDay)