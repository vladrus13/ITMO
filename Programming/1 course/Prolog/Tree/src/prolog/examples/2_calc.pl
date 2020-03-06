% Recursive Fibonacci numbers

fib(1, 1).
fib(2, 1).
fib(N, R) :-
    N > 2,
    N1 is N - 1,
 N2 is N - 2,
    fib(N1, R1), fib(N2, R2),
    R is R1 + R2.

/*
?- fib(3, R).
   R / 2
?- fib(10, R).
   R / 55
?- fib(20, R).
   R / 6765
?- fib(30, R). % Takes 10 sec
   R / 832040
*/

% Fibonacci numbers with accumulating parameters

fib_acc(N, R) :- fib_acc_rec(N, 1, 1, R).

fib_acc_rec(1, R, B, R).
fib_acc_rec(N, A, B, R) :-
    N > 1,
    N1 is N - 1,
    B1 is A + B,
    fib_acc_rec(N1, B, B1, R).


/*
?- fib_acc(3, R).
   R / 2
?- fib_acc(10, R).
   R / 55
?- fib_acc(20, R).
   R / 6765
?- fib_acc(30, R).
   R / 832040
?- fib_acc(100, R).
   R / 3736710778780434371
*/


% Memoized Fibonacci numbers

fib_mem(N, R) :- fib_table(N, R), !. % Read from table
fib_mem(1, 1).
fib_mem(2, 1).
fib_mem(N, R) :-
    N > 0,
    N1 is N - 1, fib_mem(N1, R1),
    N2 is N - 2, fib_mem(N2, R2),
    R is R1 + R2,
    assert(fib_table(N, R)). % Write to table

/*
?- fib_mem(3, R).
   R / 2
?- fib_mem(10, R).
   R / 55
?- fib_mem(20, R).
   R / 6765
?- fib_mem(30, R).
   R / 832040
?- fib_mem(100, R).
   R / 3736710778780434371
*/



% Exponentiation

power(0, _, 0).
power(_, 0, 1).
power(1, _, 1).
power(A, 1, A).
power(A, B, R) :-
   B > 1,
   B1 is B - 1,
   power(A, B1, R1),
   R is A * R1.

/*
?- power(0, 0, R).
   R / 0
   R / 1
?- power(3, 0, R).
   R / 1
?- power(3, 2, R).
   R / 9
?- power(2, 10, R).
   R / 1024
?- power(1, 10000000, R).
   R / 1
*/

% Fast exponentiation

fast_power(_, 0, 1).
fast_power(A, B, R) :-
   B > 0,
   1 is mod(B, 2),
   B1 is B - 1,
   fast_power(A, B1, R1),
   R is A * R1.
fast_power(A, B, R) :-
   B > 0,
   0 is mod(B, 2),
   B1 is div(B, 2),
   fast_power(A, B1, R1),
   R is R1 * R1.

/*
?- fast_power(0, 0, R).
   R / 1
?- fast_power(3, 0, R).
   R / 1
?- fast_power(3, 2, R).
   R / 9
?- fast_power(2, 10, R).
   R / 1024
?- fast_power(1, 10000000, R).
   R / 1
?- fast_power(3, 10000000, R).
   R / 385609709189952001
*/


% Reversible computation
/*
?-R is 2 + 3.
  5
?-5 is 2 + R.
  Error: Type error in argument 2 of is(5,'+'(2,R_e0))
*/

inc(N, R) :- number(N), !, R is N + 1.
inc(N, R) :- number(R), !, N is R - 1.

/*
?- inc(10, R).
   R / 11
?- inc(R, 10).
   R / 9
*/