{
module Grammar (parseExpression) where

import Types
import Lexer
}

%name parseExpression
%tokentype { Token }
%error { parseError }

%token
    VARIABLE        {TokenVariable $$}
    AND             {TokenAnd}
    OR              {TokenOr}
    IMPLICATION     {TokenImplication}
    NOT             {TokenNot}
    OPENBRACKET     {TokenOpenBracket}
    CLOSEBRACKET    {TokenCloseBracket}

%%

Expression : OrCut IMPLICATION Expression  {BinaryOperator Implication $1 $3}
            | OrCut                        {$1}

OrCut : OrCut OR AndCut {BinaryOperator Or $1 $3}
    | AndCut            {$1}

AndCut : AndCut AND FileCut {BinaryOperator And $1 $3}
        | FileCut           {$1}

FileCut : VARIABLE                              {Variable $1}
        | NOT FileCut                           {Not $2}
        | OPENBRACKET Expression CLOSEBRACKET   {$2}

{
parseError :: [Token] -> a
parseError e = error ("parseError " ++ (show e))
}