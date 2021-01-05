grammar Program;
import GrammarLexer;
// ------ PROGRAM

program : headers (block)*;

// ------ HEADERS

headers : header*;

header : Include '<' Word (Point Word)? '>' | Include '"' Word (Point Word)? '"';

// ------ BLOCK

block : (function | r_class | struct | using)+;

// ------ FUNCTION

function
    : type Word OpenBracket arguments CloseBracket OpenFigureBracket body CloseFigureBracket    # fullFunction
    | type Word OpenBracket arguments CloseBracket Semicolon                                    # notFullFunction
    ;
arguments : (argument Comma)* argument | ;
argument : type (references)? (Word)?;
references : '*'+;

// ------ CLASS AND STRUCT

r_class : Class Word OpenFigureBracket classOrStructBody CloseFigureBracket;
struct : Struct Word OpenFigureBracket classOrStructBody CloseFigureBracket;
classOrStructBody : (field | function)*;
field : type Word Semicolon | type expression Semicolon;

// ------ USING

using : 'using' Word Word Semicolon;

// ------ BODY
body : (maybeExpression | r_for | r_while | instruction | r_if | field | r_switch)*;
r_for : For OpenBracket field maybeExpression expression CloseBracket OpenFigureBracket body CloseFigureBracket;
r_while : While OpenBracket expression CloseBracket OpenFigureBracket body CloseFigureBracket;
r_if : If OpenBracket expression CloseBracket OpenFigureBracket body CloseFigureBracket;
r_switch : Switch OpenBracket expression CloseBracket OpenFigureBracket (r_case)+ CloseFigureBracket;
r_case : Case expression Colon body;

instruction : (Delete | Continue | Break | (Return expression)) Semicolon;
maybeExpression : expression Semicolon | Semicolon | field;
expression
    : value singleAssignOperator                                            #singleAssignExpression
    | expression (binaryAssignOperator | binaryOperator) expression         #binaryExpression
    | unaryOperation expression                                             #unaryExpression
    | OpenBracket expression CloseBracket                                   #inBracketExpression
    | Word OpenBracket expression CloseBracket                              #callingFunctionExpression
    | Word OpenSqBracket expression CloseSqBracket                          #vectorExpression
    | Word (Point | Pointer | Child) expression                             #pointerExpression
    | value                                                                 #valueExpression
    |                                                                       #emptyExpression
    ;

// ------ EXPRESSIONS

value : AnyInQuoutes | Number | Word;

binaryAssignOperator : Assign | PlusAssign | MinusAssign | MultiplyAssign | DivAssign | ModAssign | XorAssign | AndAssign |
    OrAssign | LeftShiftAssign | RightShiftAssign;
type : Int | LongLong | Auto | Bool | Double | LongDouble | Void | String | Vector '<' type '>';
binaryOperator : Plus | Minus | Multiply | Div | Mod | Xor | And | Or | Less | Greater | Equal | NotEqual | LessEqual |
    GreaterEqual | AndAnd | OrOr | LeftShift | RightShift;
unaryOperation  : Not;
singleAssignOperator : PlusPlus | MinusMinus;