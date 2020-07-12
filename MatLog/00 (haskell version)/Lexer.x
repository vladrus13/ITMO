{
module Lexer where
}

%wrapper "basic"

$alphabet = [A-Z]
$variable = [$alphabet 0-9 \']
$space = [\ \t \r]

tokens :-
    $white+                 ;
    \(                      {\x -> TokenOpenBracket}
    \)                      {\x -> TokenCloseBracket}
    \|                      {\x -> TokenOr}
    !                       {\x -> TokenNot}
    &                       {\x -> TokenAnd}
    "->"                    {\x -> TokenImplication}
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
    deriving Show
}
