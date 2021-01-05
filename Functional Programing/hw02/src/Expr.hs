{-# LANGUAGE InstanceSigs #-}
module Expr where

-- | Expression data
data Expr
    = Const Int
    | Sum Expr Expr
    | Subtruct Expr Expr
    | Multiply Expr Expr
    | Divide Expr Expr
    | Power Expr Expr

-- | ArithmeticError
data ArithmeticalError
    = DivideByZero {message :: String}
    | PowerByNegate {message :: String}
    deriving (Eq)

-- | Show instance for ArithmeticalError
instance Show ArithmeticalError where
    show :: ArithmeticalError -> String
    show (DivideByZero message) = "Division by zero " ++ message
    show (PowerByNegate message) = "Power by negate " ++ message

-- | Instance Show for Expression
instance Show Expr where
    show :: Expr -> String
    show (Const a) = show a
    show (Sum a b) = "(" ++ (show a) ++ " + " ++ (show b) ++ ")"
    show (Subtruct a b) = "(" ++ (show a) ++ " - " ++ (show b) ++ ")"
    show (Multiply a b) = "(" ++ (show a) ++ " * " ++ (show b) ++ ")"
    show (Divide a b) = "(" ++ (show a) ++ " / " ++ (show b) ++ ")"
    show (Power a b) = "(" ++ (show a) ++ " ^ " ++ (show b) ++ ")"

-- | Evaluate the Expression. Can give DivideByZero ArithmeticalError if we divide by zero, or PowerByNegate ArithmeticalError if we power by negative
eval :: Expr -> Either ArithmeticalError Int
eval (Const a) = return a
eval (Sum a b) = eval a >>= (\x -> eval b >>= (\y -> return (x + y)))
eval (Subtruct a b) = eval a >>= (\x -> eval b >>= (\y -> return (x - y)))
eval (Multiply a b) = eval a >>= (\x -> eval b >>= (\y -> return (x * y)))
eval (Divide a b) = do
    y <- eval b
    if y == 0
        then Left (DivideByZero ("at expression " ++ (show (Divide a b))))
        else do
            x <- eval a
            return (x `div` y)
eval (Power a b) = do
    y <- eval b
    if y < 0
        then Left (PowerByNegate ("at expression " ++ (show (Power a b))))
        else do
            x <- eval a
            return (x ^ y)