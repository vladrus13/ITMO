module Types (Expression (..), Operator (..))where

import Data.List (intercalate)

data Expression = BinaryOperator Operator Expression Expression
                | Not Expression
                | Variable String

data Operator = And
                | Or
                | Implication

brackets expression = concat ["(", expression, ")"]

instance Show Expression where
    show (BinaryOperator operator left right) = brackets (intercalate "," [show operator, show left, show right])
    show (Not expression) = brackets ('!' : show expression)
    show (Variable string) = string

instance Show Operator where
    show Implication = "->"
    show Or = "|"
    show And = "&"