{
module FileExpression (parseTask) where

import ManyExpression
import Lexer
}

%name parseTask
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
    TOURNIQUET      {TokenTourniquet}
    COMMA           {TokenComma}
%%

Task : Hypotheses TOURNIQUET Expression    {Task $1 $3}
     | TOURNIQUET Expression            {Task [] $2}
     
Hypotheses : Expression COMMA Hypotheses  {$1 : $3}
                | Expression                {[$1]}

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