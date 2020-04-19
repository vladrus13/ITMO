module Main where

import System.IO
import Data.Maybe
import Data.List (foldl', sortOn)
import Data.Map.Strict as Map(Map, insert, empty, singleton, foldlWithKey', member, lookup, toList, fromList, update)
import Data.IntMap.Strict as IntMap(insert, singleton, (!), member, notMember)

import GrammarExpression
import FileExpression
import Lexer
import ManyExpression

main :: IO()
main = do 
    task <- getLine
    readerLoop(generateProof . generateTask $ task)

readerLoop :: Proof -> IO()
readerLoop proof = do
    ineof <- isEOF
    if ineof
        then if isProof proof
            then putStrLn "Proof is incorrect"
            else case minimizer . sortProof $ proof of
                Nothing -> putStrLn "Proof is incorrect"
                Just p -> showProof p
    else do ex <- getLine
            case addExpression (generateExpression ex) proof of
                Nothing -> putStrLn "Proof is incorrect"
                Just p -> readerLoop p

showProof :: SortedProof -> IO()
showProof sp = do
    putStrLn . show $ (task sp)
    showListt showExpression (length . expression $ sp) (expression sp)

showExpression (ex, (ty, _)) counter = "[" ++ show counter ++ ". " ++ show ty ++ "]" ++ show ex

showListt :: (a -> Int -> String) -> Int -> [a] -> IO()
showListt _ _ [] = do
    putStrLn ""
showListt f n (h:t) = do
    showListt f (n - 1) t
    putStrLn $ f h n 

generateTask :: String -> Task
generateTask = parseTask . alexScanTokens
generateExpression :: String -> Expression
generateExpression = parseExpression . alexScanTokens
generateProof task = Proof (Map.fromList generatePair) (answer1 $ task) Map.empty Map.empty 1 False where
    generatePair = fst $ foldl' (\(a, n) h -> ((h, n) : a, n + 1)) ([], 1) (contexts task)

addExpression :: Expression -> Proof -> Maybe Proof
addExpression expression proof = 
    if (Map.member expression (expressions proof))
        then checked
        else case generator of
            Nothing -> Nothing
            Just x -> Just $ proof {
                expressions = Map.insert expression (x, size proof) (expressions proof),
                size = (size proof) + 1,
                parts = updateParts expression,
                isProof = False
            } 
    where   generator | isAxiom         = Just $ Axiom indexAxiom
                  | isHypothesis        = Just $ Hypothesis indexHypothesis
                  | isModusPonens       = Just $ uncurry ModusPonens (fromJust indexesModusPonens)
                  | otherwise = Nothing
            checked = if expression == answer proof
                then Just $ proof { expressions = Map.update (\(t, _) -> Just (t, size proof)) expression (expressions proof),
                    size = (size proof) + 1,
                    isProof = False}
                else Just $ proof {isProof = True}
            
            
            isHypothesis = indexHypothesis /= -1
            indexHypothesis = case Map.lookup expression (context proof) of
                Nothing -> -1
                Just x -> x

            
            isModusPonens = isJust indexesModusPonens
            indexesModusPonens :: Maybe (Int, Int)
            indexesModusPonens = case Map.lookup expression $ parts proof of
                Nothing -> Nothing
                Just indexList -> foldr folder Nothing indexList
                where folder _ (Just x) = Just x
                      folder(expr, counter) _ = case Map.lookup expr (expressions proof) of
                        Nothing -> Nothing
                        Just y -> Just (counter, snd y)
            updateParts (BinaryOperator Implication a b) = if (Map.member b $ parts proof) 
                then Map.update (\x -> Just $ (a, size proof):x) b $ parts proof
                else Map.insert b [(a, size proof)] $ parts proof
            updateParts _ = parts proof

            isAxiom = indexAxiom /= -1
            indexAxiom = depthRecursion 1 axioms
                where depthRecursion _ [] = -1
                      depthRecursion pos (h:t) = if expression `compare` h then pos else depthRecursion (pos + 1) t 
            compare expr pattern = recursiveCompare expr pattern /= Nothing
                where   recursiveCompare (BinaryOperator op1 exl exr) (BinaryOperator op2 patl patr) = 
                            if (op1 /= op2)
                            then Nothing
                            else compAssoc (recursiveCompare exl patl) (recursiveCompare exr patr)
                        recursiveCompare (Not expr) (Not pattern) = recursiveCompare expr pattern
                        recursiveCompare expr (Variable s) = Just (Map.singleton s expr)
                        recursiveCompare _ _ = Nothing

                        compAssoc _ Nothing = Nothing
                        compAssoc Nothing _ = Nothing
                        compAssoc left (Just right) = Map.foldlWithKey' assocFolder left right

                        assocFolder Nothing _ _ = Nothing
                        assocFolder (Just was) f s
                            | Map.lookup f was == Nothing = Just (Map.insert f s was)
                            | Map.lookup f was == Just s = Just was
                            | otherwise = Nothing

sortProof proof = 
    SortedProof (Task ((map fst) . (sortOn snd) . Map.toList . context $ proof) (answer proof))
        ((sortOn (snd . snd)) . Map.toList . expressions $ proof)

minimizer proof = 
    if null (expression proof) || (fst . last $ (expression proof)) /= (answer1 . task $ proof)
        then Nothing
        else Just (proof {expression = generatedExpression})
    where   generatedExpression = fstMAXIMA $ foldl' folder ([], usable, 1, 1) (expression proof)
            fstMAXIMA (x, _, _, _) = x
            folder (ready, mapper, counter, current) ex =
                if IntMap.notMember current mapper
                    then (ready, mapper, counter, current + 1)
                    else (process ex ready mapper counter, IntMap.insert current counter mapper, counter + 1, current + 1)
            process (ex, (ModusPonens x y, _)) ready mapper counter =
                (ex, (ModusPonens (mapper IntMap.! x) (mapper IntMap.! y), 0)) : ready
            process el ready mapper _ = el : ready

            l = length . expression $ proof
            usable = fst $ foldr folder' (IntMap.singleton l 0, l)  (expression proof)
            folder' (_, (ModusPonens x y, _)) (was, counter) = (mapConstruct counter was x y, counter - 1)
            folder' _ (was, counter) = (was, counter - 1)
            mapConstruct counter mapper x y =
                if IntMap.member counter mapper
                    then (IntMap.insert x 0) . (IntMap.insert y 0) $ mapper
                    else mapper

axioms = map generateExpression 
    [
    "A -> (B -> A)",
    "(A -> B) -> (A -> B -> C) -> (A -> C)",
    "A -> B -> A & B",
    "A & B -> A",
    "A & B -> B",
    "A -> A | B",
    "B -> A | B",
    "(A -> C) -> (B -> C) -> (A | B -> C)",
    "(A -> B) -> (A -> !B) -> !A",
    "!!A -> A"
    ]