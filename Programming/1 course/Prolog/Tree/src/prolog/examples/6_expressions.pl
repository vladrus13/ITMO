% Imported from lists.pl
lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

% Constructors
variable(Name, variable(Name)).
const(Value, const(Value)).
add(A, B, bin(add, A, B)).
sub(A, B, bin(sub, A, B)).
mul(A, B, bin(mul, A, B)).
dvd(A, B, bin(dvd, A, B)).

example(E) :-
    variable(x, Vx), variable(y, Vy), variable(z, Vz), const(100, C),
    sub(Vy, Vz, Syz), mul(Vx, Syz, MxSyz), add(MxSyz, C, E).
/*
?- example(E).
   E / bin(add,bin(mul,variable(x),bin(sub,variable(y),variable(z))),const(100))
*/


% Binary operatoin definitions
bin(add, A, B, R) :- R is A + B.
bin(sub, A, B, R) :- R is A - B.
bin(mul, A, B, R) :- R is A * B.
bin(dvd, A, B, R) :- R is A / B.

% Evaluation
eval(const(V), Vars, V).
eval(variable(Name), Vars, R) :- lookup(Name, Vars, R).
eval(bin(Op, A, B), Vars, R) :- eval(A, Vars, AV), eval(B, Vars, BV), bin(Op, AV, BV, R).

/*
?- example(E), eval(E, [(x, 1), (y, 2), (z, 3)], R).
   R / 99
*/


% Term-based converstion to string representation and parsing

expr_term(variable(Name), Name) :- atom(Name), !.
expr_term(const(Value), Value) :- number(Value), !.
expr_term(bin(Op, A, B), R) :- R =.. [Op, AT, BT], expr_term(A, AT), expr_term(B, BT).

expr_text(E, S) :- expr_term(E, T), text_term(S, T).
text_expr(S, E) :- text_term(S, T), expr_term(E, T).

/*
?- example(E), expr_term(E, R).
   R / add(mul(x,sub(y,z)),100)
?- expr_term(E,add(mul(x,sub(y,z)),100)), eval(E, [(x, 1), (y, 2), (z, 3)], R).
   E / bin(add,bin(mul,variable(x),bin(sub,variable(y),variable(z)))
   R / 102
?- example(E), expr_text(E, R).
   R / 'add(mul(x,sub(y,z)),100)'
?- text_expr('add(mul(x,sub(y,z)),100)', E), eval(E, [(x, 1), (y, 2), (z, 3)], R).
   E / bin(add,bin(mul,variable(x),bin(sub,variable(y),variable(z))),const(100))
   R / 99
*/

% Token-based conversion to string and parsing

all_member([], _).
all_member([H | T], Values) :- member(H, Values), all_member(T, Values).

expr_chars(variable(Name), [Char]) :- atom_chars(Name, [Char]), member(Name, [x, y, z]), !.
expr_chars(const(Value), Chars) :- all_member(Chars, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']), number_chars(Value, Chars).
expr_chars(bin(Op, A, B), R) :-
  append(OpAB, [')'], R),
  append(OpA, [',' | RB], OpAB),
  append(ROp, ['(' | RA], OpA),
  atom_chars(Op, ROp), member(Op, [add, sub, mul, del]),
  expr_chars(A, RA), expr_chars(B, RB),
  !.

expr_atom(E, S) :- expr_chars(E, C), atom_chars(S, C).
atom_expr(S, E) :- atom_chars(S, C), print(C), nl, expr_chars(E, C).

/*
?- example(E), expr_chars(E, R).
   R / [a,d,d,'(',m,u,l,'(',x,',',s,u,b,'(',y,',',z,')',')',',','1','0','0',')']
?- expr_chars(E, [a,d,d,'(',m,u,l,'(',x,',',s,u,b,'(',y,',',z,')',')',',','1','0','0',')']), eval(E, [(x, 1), (y, 2), (z, 3)], R).
   E / bin(add,bin(mul,variable(x),bin(sub,variable(y),variable(z))),const(100))
   R / 99
?- example(E), expr_atom(E, R).
   R / 'add(mul(x,sub(y,z)),100)'
?- atom_expr('add(mul(x,sub(y,z)),100)', E), eval(E, [(x, 1), (y, 2), (z, 3)], R)..
   E / bin(add,bin(mul,variable(x),bin(sub,variable(y),variable(z))),const(100))
   R / 99
*/
