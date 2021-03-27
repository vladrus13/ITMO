{-# LANGUAGE BangPatterns #-}
import Criterion.Main ( defaultMain, bench, bgroup, whnf )
import Geometry.Point(Point(..), perimeter, longPerimeter)

-- | Generate random point on position 'pos' and seed 'seed'
randomPoint :: Int -> Int -> Point
randomPoint pos gen = Point x y where
    x = (gen * gen * gen * pos * 2 + gen * gen * pos * 3 + gen * 2) `mod` 1000000000
    y = (gen * gen * gen * pos * 3 + gen * gen * pos * 2 + gen * 3) `mod` 1000000000

-- | Generate a list with random points on 'acc' with length 'times' and seed 'seed'
randomListPoints :: [Point] -> Int -> Int -> [Point]
randomListPoints !acc 0 _ = acc
randomListPoints !acc times seed = randomListPoints (randomPoint times seed : acc) (times - 1) (2 * seed `mod` 1000000000)

-- | Runner for tests
main :: IO ()
main = defaultMain [
    bgroup "perimeter" [
        bench "not-random" $ whnf perimeter [Point 0 0, Point 1 1, Point 1 0],
        bench "2" $ whnf perimeter (randomListPoints [] 1 123456),
        bench "10^3" $ whnf perimeter (randomListPoints [] 1000 12345),
        bench "10^6" $ whnf perimeter (randomListPoints [] 1000000 38422),
        bench "10^6-original" $ whnf longPerimeter (randomListPoints [] 1000000 38422),
        bench "10^7" $ whnf perimeter (randomListPoints [] 10000000 123),
        bench "10^7-original" $ whnf longPerimeter (randomListPoints [] 10000000 123)
        -- bench "10^8" $ whnf perimeter (randomListPoints [] 100000000 123)
        ]
    ]