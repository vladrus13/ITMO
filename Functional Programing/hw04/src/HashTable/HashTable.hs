-- | Hash table class
module HashTable.HashTable (newCHT, getCHT, putCHT, sizeCHT) where

import Control.Concurrent.STM (TVar, newTVar)
import Control.Concurrent.STM.TVar (readTVar, readTVarIO, writeTVar)
import Control.Monad (forM_)
import Control.Monad.STM (STM, atomically)
import Data.Hashable (Hashable (hash))
import Data.Vector (Vector, replicateM, (!))

-- | Concurrent Hash Table structure
--
-- https://www.tutorialspoint.com/data_structures_algorithms/hash_data_structure.htm
data ConcurrentHashTable k v = ConcurrentHashTable
  { elements :: TVar (Vector (TVar (Maybe (k, v)))),
    size :: TVar Int
  }

-- | Create new hash table structure
newCHT :: IO (ConcurrentHashTable k v)
newCHT = atomically $ do
  vector <- replicateM 32 $ newTVar Nothing
  table <- newTVar vector
  sizeTable <- newTVar 0
  return $ ConcurrentHashTable table sizeTable

-- | Get element from vector from hash table
getElement :: (Hashable k, Eq k) => k -> Vector (TVar (Maybe (k, v))) -> STM (TVar (Maybe (k, v)))
getElement key vector = for 0
  where
    start = hash key
    sizeTable = length vector
    for i = do
      let index = (start + i) `mod` sizeTable
      let prima = vector ! index
      element <- readTVar prima
      case element of
        Nothing -> return prima
        Just (elementKey, _) ->
          if elementKey == key then return prima else for (i + 1)

-- | Get element from hash table
getCHT :: (Hashable k, Eq k) => k -> ConcurrentHashTable k v -> IO (Maybe v)
getCHT key hashTable = atomically $ do
  vector <- readTVar (elements hashTable)
  tVar <- getElement key vector
  element <- readTVar tVar
  case element of
    Nothing -> return Nothing
    Just (_, value) -> return (Just value)

-- | Put element to hash table
putCHT :: (Hashable k, Eq k) => k -> v -> ConcurrentHashTable k v -> IO ()
putCHT key value hashTable = atomically $ do
  vector <- readTVar (elements hashTable)
  sizeTable <- readTVar (size hashTable)
  elementContainer <- getElement key vector
  element <- readTVar elementContainer
  case element of
    Just _ -> writeTVar elementContainer (Just (key, value))
    Nothing ->
      if (sizeTable + 1) == length vector
        then do
          newVector <- remake (key, value) vector
          writeTVar (elements hashTable) newVector
          writeTVar (size hashTable) (sizeTable + 1)
        else do
          writeTVar elementContainer (Just (key, value))
          writeTVar (size hashTable) (sizeTable + 1)

-- | Create new vector from old with elements, but 1.5 size bigger
remake :: (Hashable k, Eq k) => (k, v) -> Vector (TVar (Maybe (k, v))) -> STM (Vector (TVar (Maybe (k, v))))
remake added vector = do
  newVector <- replicateM (2 * length vector) (newTVar Nothing)
  let addElement (key, value) = do
        position <- getElement key newVector
        writeTVar position (Just (key, value))
  forM_
    vector
    ( \x -> do
        element <- readTVar x
        forM_ element addElement
    )
  addElement added
  return newVector

-- | Get size of hash table
sizeCHT :: ConcurrentHashTable k v -> IO Int
sizeCHT hashTable = readTVarIO (size hashTable)