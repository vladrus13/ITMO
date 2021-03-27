{-# LANGUAGE AllowAmbiguousTypes #-}
{-# LANGUAGE BlockArguments #-}
{-# LANGUAGE ConstrainedClassMethods #-}
{-# LANGUAGE DefaultSignatures #-}
{-# LANGUAGE FlexibleContexts #-}
{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE GADTs #-}
{-# LANGUAGE GeneralizedNewtypeDeriving #-}
{-# LANGUAGE Rank2Types #-}
{-# LANGUAGE TypeFamilies #-}

-- | Main module of HalyavaScript hw
module HalyavaScript.Script (ScriptType (..), Script (..), Intepreter (..), runP) where

import Control.Monad (join, liftM2)
import Control.Monad.Extra (ifM)
import Control.Monad.Loops (whileM_)
import Control.Monad.ST (ST, runST)
import Data.STRef (STRef, newSTRef, readSTRef, writeSTRef)

infixl 7 @*

infixl 6 @+, @-

infix 4 @<, @<=, @>, @>=, @==, @!=, @=

infixr 3 @&&

infixr 2 @||

infixl 1 @#

infixr 0 @.

infixr 0 @>>=

-- | Type for script
class (Show a) => ScriptType a where
  defaultValue :: a

instance ScriptType Int where defaultValue = 0

instance ScriptType Bool where defaultValue = False

instance ScriptType String where defaultValue = ""

instance ScriptType Double where defaultValue = 0.0

-- | Class script
class Script interpreter where
  type Variable interpreter :: * -> *

  -- | Create a variable with this type
  createType :: (ScriptType a) => a -> interpreter (Variable interpreter a)

  -- | Create int
  int :: Int -> interpreter (Variable interpreter Int)
  int = createType

  -- | Create Double
  double :: Double -> interpreter (Variable interpreter Double)
  double = createType

  -- | Create Bool
  bool :: Bool -> interpreter (Variable interpreter Bool)
  bool = createType

  -- | Create String
  string :: String -> interpreter (Variable interpreter String)
  string = createType

  -- | Get variable for real haskell
  getType :: ScriptType t => Variable interpreter t -> interpreter t

  -- | Get Script variable from real
  up :: (ScriptType a) => a -> interpreter a
  default up :: (Applicative interpreter, ScriptType a) => a -> interpreter a
  up = pure

  -- | Separator of command
  (@#) :: interpreter a -> interpreter b -> interpreter b
  default (@#) :: (Monad interpreter) => interpreter a -> interpreter b -> interpreter b
  (@#) = (>>)

  -- | Create new variable
  create :: (ScriptType a) => interpreter (Variable interpreter a) -> (Variable interpreter a -> interpreter b) -> interpreter b
  default create :: (Monad interpreter, ScriptType a) => interpreter (Variable interpreter a) -> (Variable interpreter a -> interpreter b) -> interpreter b
  create = (>>=)

  -- | Function of one argument
  sFun1 :: (interpreter a -> interpreter b) -> interpreter (a -> interpreter b)
  default sFun1 :: (Applicative interpreter) => (interpreter a -> interpreter b) -> interpreter (a -> interpreter b)
  sFun1 function = pure (function . pure)

  -- | Function of two argument
  sFun2 :: (Applicative interpreter) => (interpreter a -> interpreter b -> interpreter c) -> interpreter (a -> b -> interpreter c)
  sFun2 function = pure (\a b -> function (pure a) (pure b))

  -- | Functiono of three argument
  sFun3 :: (Applicative interpreter) => (interpreter a -> interpreter b -> interpreter c -> interpreter d) -> interpreter (a -> b -> c -> interpreter d)
  sFun3 function = pure (\a b c -> function (pure a) (pure b) (pure c))

  -- | While
  sWhile :: interpreter Bool -> interpreter () -> interpreter ()
  default sWhile :: (Monad interpreter) => interpreter Bool -> interpreter () -> interpreter ()
  sWhile = whileM_

  -- | If
  sIf :: (Monad interpreter) => interpreter Bool -> interpreter () -> interpreter () -> interpreter ()
  sIf = ifM

  -- | "Functor"
  (@.) :: (Monad interpreter) => interpreter (a -> b) -> interpreter a -> interpreter b

  -- | "Cut monad"
  (@>>=) :: interpreter (a -> interpreter b) -> interpreter a -> interpreter b

  -- | Apply to variable real-haskell variable
  (@@=) :: (Monad interpreter, ScriptType a) => Variable interpreter a -> a -> interpreter ()
  a @@= b = a @= up b

  -- | Set to variable another variable
  (@=) :: (ScriptType a) => Variable interpreter a -> interpreter a -> interpreter ()
  default (@=) :: (Monad interpreter, ScriptType a) => Variable interpreter a -> interpreter a -> interpreter ()
  a @= b = b >>= (a @@=)

  -- | Equals
  (@==) :: (Eq a, ScriptType a) => interpreter a -> interpreter a -> interpreter Bool

  -- | Not equals
  (@!=) :: (Eq a, ScriptType a) => interpreter a -> interpreter a -> interpreter Bool

  -- | Greater
  (@>) :: (Ord a, ScriptType a) => interpreter a -> interpreter a -> interpreter Bool

  -- | Greater or equals
  (@>=) :: (Ord a, ScriptType a) => interpreter a -> interpreter a -> interpreter Bool

  -- | Less
  (@<) :: (Ord a, ScriptType a) => interpreter a -> interpreter a -> interpreter Bool

  -- | Less or equals
  (@<=) :: (Ord a, ScriptType a) => interpreter a -> interpreter a -> interpreter Bool

  -- | And
  (@&&) :: (Ord a, ScriptType a) => interpreter Bool -> interpreter Bool -> interpreter Bool

  -- | Or
  (@||) :: (Ord a, ScriptType a) => interpreter Bool -> interpreter Bool -> interpreter Bool

  -- | Plus
  (@+) :: (Num a, ScriptType a) => interpreter a -> interpreter a -> interpreter a

  -- | Multiply
  (@*) :: (Num a, ScriptType a) => interpreter a -> interpreter a -> interpreter a

  -- | Minus
  (@-) :: (Num a, ScriptType a) => interpreter a -> interpreter a -> interpreter a

-- | Executor
newtype Intepreter state a = Intepreter {run :: ST state a}
  deriving (Functor, Applicative, Monad)

-- | Runner for program
runP :: (forall s. Intepreter s a) -> a
runP state = runST (run state)

-- | Executor of programs
instance Script (Intepreter state) where
  type Variable (Intepreter state) = STRef state
  createType = Intepreter . newSTRef
  getType = Intepreter . readSTRef
  a @@= b = Intepreter $ writeSTRef a b
  (@.) = (<*>)
  (@==) = liftM2 (==)
  (@!=) = liftM2 (/=)
  (@>) = liftM2 (>)
  (@<) = liftM2 (<)
  (@>=) = liftM2 (>=)
  (@<=) = liftM2 (<=)
  (@+) = liftM2 (+)
  (@-) = liftM2 (-)
  (@*) = liftM2 (*)
  (@&&) = liftM2 (&&)
  (@||) = liftM2 (||)
  a @>>= b = join (a <*> b)