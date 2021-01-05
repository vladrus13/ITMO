lexer grammar GrammarLexer;

// Types
Int         : 'int';
LongLong    : 'long long';
Auto        : 'auto';
Bool        : 'bool';
Double      : 'double';
LongDouble  : 'long double';
Void        : 'void';
String      : 'string';
Vector      : 'vector';

PlusPlus                : '++';
MinusMinus              : '--';

Not             : '!' | 'not';

PlusAssign      : '+=';
MinusAssign     : '-=';
MultiplyAssign  : '*=';
DivAssign       : '/=';
ModAssign       : '%=';
XorAssign       : '^=';
AndAssign       : '&=';
OrAssign        : '|=';
LeftShiftAssign : '<<=';
RightShiftAssign : '>>=';

Plus            : '+';
Minus           : '-';
Multiply        : '*';
Div             : '/';
Mod             : '%';
Xor             : '^';
And             : '&';
Or              : '|';
Less            : '<';
Greater         : '>';
Equal           : '==';
NotEqual        : '!=';
LessEqual       : '<=';
GreaterEqual    : '>=';
AndAnd          : '&&' | 'and';
OrOr            : '||' | 'or';
LeftShift       : '<<';
RightShift      : '>>';
Assign          : '=';

Tilde           : '~';
Comma           : ',';
Semicolon       : ';';
OpenBracket     : '(';
CloseBracket    : ')';
OpenFigureBracket : '{';
CloseFigureBracket : '}';
Point           : '.';
Pointer         : '->';
OpenSqBracket   : '[';
CloseSqBracket  : ']';
Child           : '::';
Colon           : ':';

Break : 'break';
Return : 'return';
Continue : 'continue';
Delete : 'delete';
Include : '#include';
For : 'for';
While : 'while';
Class : 'class';
Struct : 'struct';
If : 'if';
Switch : 'switch';
Case : 'case';

Number      : FloatNumber | Digits | Boolean;
FloatNumber : Digits '.' Digits;
Digits      : Digit+;
Digit       : [0-9];
Boolean     : True | False;
True        : 'true';
False       : 'false';

Word : [a-zA-Z_]+;
AnyInQuoutes : '"'[a-zA-Z ,!?]+'"';

WS : [ \t\n\r]+ -> skip;