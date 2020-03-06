% Length of the list

count([], 0).
count([_ | T], R) :- count(T, TR), R is TR + 1.
% count/2 is the same as length/2 from standard library

/*
?- count([], R).
   R / 0
?- count([1, 2, 3], R).
   R / 3
*/


% Checks whether list contains element.

contains(V, [V | _]).
contains(V, [H | T]) :- V \= H, contains(V, T).
% contains/2 is the same as memeber/2 from standard library

/*
?- contains(1, [1, 2, 3]).
   yes
?- contains(3, [1, 2, 3]).
   yes
?- contains(10, [1, 2, 3]).
   no
*/


% List concatenation

concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).
% concat/3 is the same as append/3 from standard library

/*
?- concat([1, 2, 3], [], R).
   R / [1,2,3]
?- concat([1, 2, 3], [4, 5, 6], R).
   R / [1,2,3,4,5,6]
?- concat([1, 2, 3], R, [1, 2, 3, 4, 5, 6]).
   R / [4,5,6]
?- concat(R, [4, 5, 6], [1, 2, 3, 4, 5, 6]).
   R / [1,2,3]
?- concat([1 | R], [4, 5, 6], [1, 2, 3, 4, 5, 6]).
   R / [2,3]
?- concat([2 | R], [4, 5, 6], [1, 2, 3, 4, 5, 6]).
   no
*/

% Generate integer list range [N, L)

range(L, L, []).
range(N, L, [N | T]) :- N < L, N1 is N + 1, range(N1, L, T).

/*
?- range(10, 10, R).
   R / []
?- range(0, 10, R).
   R / [0,1,2,3,4,5,6,7,8,9]
*/

% Table lookup
lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

/*
?- lookup(x, [(x, 10), (y, 20)], R).
   R / 10
?- lookup(y, [(x, 10), (y, 20)], R).
   R / 20
?- lookup(z, [(x, 10), (y, 20)], R).
   no
*/


% High-order rules

map([], _, []).
map([H | T], F, [RH | RT]) :- G =.. [F, H, RH], call(G), map(T, F, RT).

% From calc.pl
inc(N, R) :- number(N), !, R is N + 1.
inc(N, R) :- number(R), !, N is R - 1.

/*
?- map([10, 20, 30], inc, R).
   R / [11,21,31]
?- map(R, inc, [10, 20, 30]).
   R / [9,19,29]
*/


% Matrix transposition

transpose([[] | _], []) :- !.
transpose(L, [RH | RT]) :- map(L, head, RH), map(L, tail, Tails), transpose(Tails, RT).

head([H | _], H).
tail([_ | T], T).

/*
?- transpose([[1, 2], [3, 4]], R)
   R / [[1,3],[2,4]]
*/

/** Zip lists. */
zip([], _, []) :- !.
zip(_, [], []) :- !.
zip([H1 | T1], [H2 | T2], [(H1, H2) | RT]) :- zip(T1, T2, RT).

/*
?- zip([1, 2, 3], [a, b, c], R).
   R / [(1,a),(2,b),(3,c)]
*/
