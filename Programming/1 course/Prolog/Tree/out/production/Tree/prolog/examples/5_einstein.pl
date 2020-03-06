% https://ru.wikipedia.org/wiki/Загадка_Эйнштейна

all_distinct([]).
all_distinct([H | T]) :- \+ member(H, T), all_distinct(T).

choose([], _).
choose([H | T], Values) :- member(H, Values), choose(T, Values), \+ member(H, T).

left(V1, V2, [V1, V2 | _]) :- !.
left(V1, V2, [H | T]) :- left(V1, V2, T).

next(V1, V2, R) :- left(V1, V2, R).
next(V1, V2, R) :- left(V2, V1, R).

solve(W, Z, R) :-
  R = [house(N1, C1, P1, D1, S1), house(N2, C2, P2, D2, S2), house(N3, C3, P3, D3, S3), house(N4, C4, P4, D4, S4), house(N5, C5, P5, D5, S5)], % На улице стоят пять домов.
  D3 = mlk,                                     % В центральном доме пьют молоко.
  N1 = nrg,                                     % Норвежец живёт в первом доме.
  member(house(eng, red, _  ,   _, _  ), R),    % Англичанин живёт в красном доме.
  member(house(spn, _  , dog,   _, _  ), R),    % У испанца есть собака.
  member(house(_  , grn, _  , cof, _  ), R),    % В зелёном доме пьют кофе.
  member(house(ukr, _  , _  , tea, _  ), R),    % Украинец пьёт чай.
  member(house(_  , _  , slu, _  , old), R),    % Тот, кто курит Old Gold, разводит улиток.
  member(house(_  , yel, _  , _  , koo), R),    % В жёлтом доме курят Kool.
  member(house(_  , _  , _  , ora, luk), R),    % Тот, кто курит Lucky Strike, пьёт апельсиновый сок.
  member(house(jap, _  , _  , _  , par), R),    % Японец курит Parliament.
  left(house(_  , whi, _  , _  , _  ),
       house(_  , grn, _  , _  , _  ), R),  % Зелёный дом стоит сразу справа от белого дома.
  next(house(_  , _  , _  , _  , che),
       house(_  , _  , fox, _  , _  ), R),  % Сосед того, кто курит Chesterfield, держит лису.
  next(house(_  , _  , hrs, _  , _  ),
       house(_  , _  , _  , _  , koo), R),  % В доме по соседству с тем, в котором держат лошадь, курят Kool.
  next(house(nrg, _  , _  , _  , _  ),
       house(_  , blu, _  , _  , _  ), R),  % Норвежец живёт рядом с синим домом.
  choose([N1, N2, N3, N4, N5], [eng, spn, ukr, jap, nrg]),
  choose([C1, C2, C3, C4, C5], [red, grn, yel, whi, blu]),
  choose([P1, P2, P3, P4, P5], [dog, slu, fox, hrs, zeb]),
  choose([D1, D2, D3, D4, D5], [mlk, cof, tea, ora, wat]),
  choose([S1, S2, S3, S4, S5], [old, koo, luk, par, che]),
  member(house(W  , _  , _  ,  wat, _  ), R), % Кто пьет воду?
  member(house(Z  , _  , zeb,  _  , _  ), R). % Кто держит зебру?

/*
?- solve(W, Z, R).
   W / nrg  Z / jap  R / [house(nrg,yel,fox,wat,koo),house(ukr,blu,hrs,tea,che),house(eng,red,slu,mlk,old),house(spn,whi,dog,ora,luk),house(jap,grn,zeb,cof,par)]
*/