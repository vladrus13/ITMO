insertobalancer((ParentKey, ParentValue), [], node((ParentKey, ParentValue), [], [])).
insertobalancer((ParentKey, ParentValue), node((LeftKey, LeftValue), L, R), node((RightKey, RightValue), P, Q)) :-
    (   ParentKey < LeftKey
    ->  insertobalancer((ParentKey, ParentValue), L, U),
        (P, (RightKey, RightValue), Q) = (U, (LeftKey, XR), R)
    ;   ParentKey > LeftKey
    ->  insertobalancer((ParentKey, ParentValue), R, U),
        (P, (RightKey, RightValue), Q) = (L, (LeftKey, LeftValue), U)
    ;   (P, (RightKey, RightValue), Q) = (L, (LeftKey, LeftValue), R)
    ).

map_get((K, V), node((K, V), _, _)).
map_get((K, V), node((VK, VV), L, _)) :- K < VK, map_get((K, V), L).
map_get((K, V), node((VK, VV), _, R)) :- K > VK, map_get((K, V), R).

tree_maker([], T, T).
tree_maker([Element|Another], T0, T) :-
    insertobalancer(Element, T0, T1),
    tree_maker(Another, T1, T).

tree_build(L,R) :-
        tree_maker(L, [], R).

map_get(T,K,V) :-
        map_get((K, V), T).

map_size([], Size) :-
    Size is 0, !.

map_size(node((K, V), L, R), Size) :-
    map_size(L, Temp),
    map_size(R, Temp2),
    Size is 1 + Temp + Temp2, !.