grammar Parser;

file : main global (token | lexem)*;

main : '!' (TokenName | LexemName) ';';
global : Varible*;

token : TokenName ':' (regularas) '*'? ';';
lexem : LexemName ':' (matches) ';';

functions : ('[' Data ']')*;

matches : match ('|' match)*;
match : (TokenName | LexemName)* ('{' functions '}')?;
regularas : regular ('|' regular)*;
regular : (Regular | StrongRegular)*;

Data : '<' (~('>'))+ '>';

Varible : '#' (~(';'))* ';';

Regular : '\'' (~('\''))+ '\'';
StrongRegular : '$\'' (~('\''))+ '\'';

TokenName : [A-Z][a-zA-Z_0-9]*;
LexemName : [a-z][a-zA-Z_0-9]*;

WS : [ \t\n\r] -> skip;