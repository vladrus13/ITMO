{-# LANGUAGE BlockArguments #-}
{-# LANGUAGE TypeFamilies #-}

-- | ToJavaScript HW
module HalyavaScript.JavaScript (Store(..), runR) where

import Data.Functor.Const (Const (..))
import HalyavaScript.Script

-- import Lens.Micro.TH (makeLenses)

-- | Store recursive function from count functions variables and indencies to string
newtype Store a = Store
  { _recursive :: Int -> Int -> Int -> String
  }

-- makeLenses ''Store

-- | Spaces on tab
spaces :: Int
spaces = 2

-- | Make count of tabs
tabs :: Int -> String
tabs t = replicate (t * spaces) ' '

-- | Make operator
operatorHelper :: Store a -> String -> Store b -> Store c
operatorHelper a operator b = Store (\functions variables indencies -> 
    tabs indencies <> "(" <> _recursive a functions variables indencies <> ") " <> operator <> " (" <> _recursive b functions variables indencies <> ")")

-- | Make operator
operatorMaker :: String -> Store a -> Store b -> Store c
operatorMaker o a = operatorHelper a o

-- | Run toJS function
runR :: Store a -> String 
runR a = _recursive a 0 0 0

-- | Make function
makeFunction :: (Int, Int, Int) -> String -> Store a -> String 
makeFunction (functions, variables, indencies) variablesS f = 
  let (variable:another) = reverse (lines (_recursive f (functions + 1) variables (indencies + 1))) in
  tabs indencies <> "function f" <> show functions <> "(" <> variablesS <> ") {\n" 
  <> unlines (reverse another) <> tabs (indencies + 1) <> "return " <> variable <> ";\n" <> tabs indencies <>"}\n"

-- | Instance script for class store
instance Script Store where
  type Variable Store = Const String

  up a = Store (\_ _ -> const $ show a)

  createType a = Store (\_ _ -> const $ show a)

  getType a = Store (\_ _ -> const $ getConst a)

  a @= b = Store (\functions variables indencies -> tabs indencies <> getConst a <> " = " <> _recursive b functions variables 0 <> ";")

  a @# b = Store (\functions variables indencies -> _recursive a functions variables indencies <> "\n" <> _recursive b functions variables indencies)

  f @. a = Store (_recursive f) @# Store (\functions variables indencies -> "f" <> show functions <> "(" <> _recursive a functions variables indencies <> ");")

  f @>>= a = Store (_recursive f) @# Store (\functions variables indencies -> "f" <> show functions <> "(" <> _recursive a functions variables indencies <> ");")

  create variable inp = Store (\functions variables indencies -> 
    tabs indencies <> "var v" <> show variables <> " = " 
    <> _recursive variable functions variables indencies <> ";\n" <> _recursive (inp (Const $ "x" <> show variables)) functions (variables + 1) indencies)

  sFun1 f = Store (\functions variables indencies -> 
    makeFunction (functions, variables, indencies) "a" (f (Store (\_ _ -> const "a"))))

  sFun2 f = Store (\functions variables indencies -> 
    makeFunction (functions, variables, indencies) "a, b" (f (Store (\_ _ -> const "a")) (Store (\_ _ -> const "b"))))

  sFun3 f = Store (\functions variables indencies -> 
    makeFunction (functions, variables, indencies) "a, b, c" (f (Store (\_ _ -> const "a")) (Store (\_ _ -> const "b")) (Store (\_ _ -> const "c"))))

  sIf c sthen selse = Store (\functions variables indencies -> 
    tabs indencies <> "if (" <> _recursive c functions variables 0 <> ") {\n" 
    <> _recursive sthen functions variables (indencies + 1) <> "} else {\n"
    <> _recursive selse functions variables (indencies + 1) <> "}")

  sWhile c sw = Store (\functions variables indencies -> 
    tabs indencies <> "while (" <> _recursive c functions variables 0 <> ") {\n" 
    <> _recursive sw functions variables (indencies + 1) <> "}")

  (@==) = operatorMaker "=="
  (@!=) = operatorMaker "!="
  (@>) = operatorMaker ">"
  (@>=) = operatorMaker ">="
  (@<) = operatorMaker "<"
  (@<=) = operatorMaker "<="
  (@&&) = operatorMaker "&&"
  (@||) = operatorMaker "||"
  (@+) = operatorMaker "+"
  (@*) = operatorMaker "*"
  (@-) = operatorMaker "-"