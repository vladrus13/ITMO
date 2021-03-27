{-# LANGUAGE BangPatterns #-}
{-# LANGUAGE DataKinds #-}
{-# LANGUAGE GADTs #-}
{-# LANGUAGE InstanceSigs #-}
{-# LANGUAGE RankNTypes #-}

-- | Point hw class
module Geometry.Point (Point (..), plus, minus, distance, fastDistance, scalarProduct, crossProduct, perimeter, longPerimeter, doubleArea) where

-- | Data point for all task 01
data Point = Point
  { xPosition :: Int,
    yPosition :: Int
  }

-- | Instance Eq for Point
instance Eq Point where
  (==) :: Point -> Point -> Bool
  a == b = (xPosition a == xPosition b) && (yPosition a == yPosition b)

-- | Instance Show for Point
instance Show Point where
  show :: Point -> String
  show p = "{x : " ++ show (xPosition p) ++ ", y : " ++ show (yPosition p) ++ "}"

-- | Apply operation to two points and get new Point
pointCoordinatesOperation :: Point -> Point -> (Int -> Int -> Int) -> Point
pointCoordinatesOperation (Point x1 y1) (Point x2 y2) operation = Point (operation x1 x2) (operation y1 y2)

-- | Plus operation for Point
plus :: Point -> Point -> Point
plus a b = pointCoordinatesOperation a b (+)

-- | Minus operation for Point
minus :: Point -> Point -> Point
minus a b = pointCoordinatesOperation a b (-)

-- | Scalar product for Point
--
-- @see https://en.wikipedia.org/wiki/Dot_product
scalarProduct :: Point -> Point -> Int
scalarProduct a b = x + y
  where
    Point x y = pointCoordinatesOperation a b (*)

-- | Cross product for Point
--
-- See https://encyclopediaofmath.org/wiki/Pseudo-scalar_product
crossProduct :: Point -> Point -> Int
crossProduct (Point x1 y1) (Point x2 y2) = x1 * y2 - x2 * y1

-- | Heron method for get sqrt from Double
--
-- See https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Babylonian_method
--
-- Best way for close points (under 10^2 distance), but worse for distant points (more 10^2)
iterationMethodGeronSqrt :: Double -> Double -> Double -> Double
iterationMethodGeronSqrt 0 a eps = if a < eps then 0 else iterationMethodGeronSqrt eps a eps
iterationMethodGeronSqrt current a eps = if abs (current - next) < eps then next else iterationMethodGeronSqrt next a eps
  where
    next = (1 / 2) * (current + (a / current))

-- | Set sqr to Double
sqr :: Double -> Double
sqr x = x * x

-- | Fast distance from Heron Sqrt between points
fastDistance :: Point -> Point -> Double
fastDistance (Point x1 y1) (Point x2 y2) = iterationMethodGeronSqrt result result 0.00001
  where
    result = sqr (fromIntegral x1 - fromIntegral x2) + sqr (fromIntegral y1 - fromIntegral y2)

-- | Distance between points
distance :: Point -> Point -> Double
distance (Point x1 y1) (Point x2 y2) = sqrt (sqr (fromIntegral x1 - fromIntegral x2) + sqr (fromIntegral y1 - fromIntegral y2))

-- | Goes along the points of polygon and collect the function on them
walk :: (Real a) => [Point] -> a -> (Point -> Point -> a) -> a
walk (p1 : ps) acc operation = abs $ walkHelper acc p1 p1 ps
  where
    walkHelper !accum first current (next : points) = walkHelper (accum + operation current next) first next points
    walkHelper !accum first current [] = accum + operation current first
walk [] acc _ = acc

-- | Take perimeter of polygon
--
-- See fastDistance
perimeter :: [Point] -> Double
perimeter ps = walk ps 0.0 fastDistance

-- | Take fast perimeter of polygon
--
-- See distance
longPerimeter :: [Point] -> Double
longPerimeter ps = walk ps 0.0 distance

-- | Take double area of polygon
--
-- See https://en.wikipedia.org/wiki/Shoelace_formula
doubleArea :: [Point] -> Int
doubleArea ps = walk ps 0 crossProduct