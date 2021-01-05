module SMA where

import Control.Monad.State (State, evalState, get, put)
import Data.Sequence (Seq, drop, empty, index, (|>))

-- | Moving state. Contain current position, sum from (current - n) to current, and all elements from (current - n + 1) to current
type MovingState t = (Int, Seq t, t)

-- | Moving answer - accumulator list
type MovingAccumulator t = [t]

-- | Make SMA https://en.wikipedia.org/wiki/Moving_average algotithm
moving :: (Fractional t) => Int -> [t] -> [t]
moving n array = evalState (stater n array) (0, empty, 0)

-- | Monad State operation for one iteration of SMA
stater :: (Fractional t) => Int -> [t] -> State (MovingState t) (MovingAccumulator t)
stater _ [] = return []
stater n (x : xs) = do
    (currentPosition, currentSequence, currentSum) <- get
    let nextPosition = currentPosition + 1
    let (nextSum, size, nextSequence) = if currentPosition >= n
        then (currentSum + x - (index currentSequence 0), n, (Data.Sequence.drop 1 currentSequence) |> x)
        else (currentSum + x, nextPosition, currentSequence |> x)
    put (nextPosition, nextSequence, nextSum)
    recusive <- stater n xs
    return ((nextSum / fromIntegral size) : recusive)