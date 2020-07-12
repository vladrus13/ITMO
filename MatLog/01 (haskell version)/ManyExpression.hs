module ManyExpression where

import Data.List (intercalate)
import qualified Data.Map.Strict as Map (Map)

data Expression = BinaryOperator Operator Expression Expression
                | Not Expression
                | Variable String
                deriving (Eq, Ord)

data Operator = And
                | Or
                | Implication
                deriving (Eq, Ord)

data Task = Task {
    contexts :: [Expression],
    answer1 :: Expression
}

data ExpressionType = Hypothesis Int | Axiom Int | ModusPonens Int Int deriving (Eq, Ord)

data Proof = Proof {
    context :: Map.Map Expression Int,
    answer :: Expression,
    expressions :: Map.Map Expression (ExpressionType, Int),
    parts :: Map.Map Expression [(Expression, Int)],
    size :: Int,
    isProof :: Bool
}

data SortedProof = SortedProof {
    task :: Task,
    expression :: [(Expression, (ExpressionType, Int))]
}

instance Show Expression where
    show (BinaryOperator operator left right) = "(" ++ intercalate " " [(show left), (show operator), (show right)] ++ ")"
    show (Not expression) = '!' : show expression
    show (Variable string) = string

instance Show Operator where
    show Implication = "->"
    show Or = "|"
    show And = "&"


instance Show Task where
    show task = (case (intercalate ", ") . (map show) . contexts $ task of 
        "" -> "" 
        line -> line ++ " ") ++ "|-" ++ (show . answer1 $ task)

instance Show ExpressionType where
    show (Hypothesis i) = "Hypothesis" ++ show i
    show (Axiom i) = "Ax. sch. " ++ show i
    show (ModusPonens i j) = "M.P. " ++ show i ++ ", " ++ show j