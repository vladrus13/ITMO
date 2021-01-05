{
module Lexer where
}

%wrapper "basic"

$alphabet = [A-Z]
$variable = [$alphabet 0-9 \']
$space = [\ \t \r]

tokens :-
    $white+                ;
    \(                      {\x -> TokenOpenBracket}
    \)                      {\x -> TokenCloseBracket}
    \|                      {\x -> TokenOr}
    !                       {\x -> TokenNot}
    &                       {\x -> TokenAnd}
    "->"                    {\x -> TokenImplication}
    "|-"                    {\x -> TokenTourniquet}
    \,                      {\x -> TokenComma}
    \n                      {\x -> TokenLine}
    $alphabet[$variable]*   {\x -> TokenVariable x}

{
data Token
    = TokenVariable String
    | TokenAnd
    | TokenCloseBracket
    | TokenImplication
    | TokenNot
    | TokenOpenBracket
    | TokenOr
    | TokenTourniquet
    | TokenComma
    | TokenLine
    deriving Show
}
