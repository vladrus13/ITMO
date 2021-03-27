import Criterion.Main ( defaultMain, bench, bgroup, whnfIO )
import Control.Concurrent.Async(mapConcurrently)
import HashTable.HashTable(newCHT, putCHT)

-- | Check concurrently put 
concurrentlyPut :: IO [()]
concurrentlyPut = do
    table <- newCHT
    mapConcurrently (\x -> putCHT (show (x `div` 10)) x table) ([1..10000] :: [Int])

-- | Main function for hash-table bench
main :: IO ()
main = defaultMain [
    bgroup "perimeter" [
        bench "concurrently put" $ whnfIO concurrentlyPut
        ]
    ]