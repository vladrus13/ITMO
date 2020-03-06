% База данных фактов

% Дисциплины осеннего семестра первого курса
course(1, fall, 'Прог').
course(1, fall, 'АиСД').
course(1, fall, 'АрхЭВМ').
course(1, fall, 'МатАн').
course(1, fall, 'ЛинАл').
course(1, fall, 'ИнЯз').

% Дисциплины весеннего семестра первого курса
course(1, spring, 'МатАн').
course(1, spring, 'ЛинАл').
course(1, spring, 'ДиМат').
course(1, spring, 'Прог').
course(1, spring, 'АиСД').
course(1, spring, 'ИнЯз').
course(1, spring, 'С++').

% Дисциплины осеннего семестра второго курса
course(2, fall, 'МатАн').
course(2, fall, 'АиСД').
course(2, fall, 'ДиМат').
course(2, fall, 'ДифУр').
course(2, fall, 'С++').
course(2, fall, 'Web').
course(2, fall, 'ИнЯз').
course(2, fall, 'БЖД').

% Дисциплины весеннего семестра второго курса
course(2, spring, 'Java').
course(2, spring, 'АиСД').
course(2, spring, 'ДиМат').
course(2, spring, 'МатАн').
course(2, spring, 'МатЛог').
course(2, spring, 'ОС').
course(2, 'spring', 'ТФКП').
'course'(2, spring, 'ИнЯз').

% Запросы фактов

/*
?- course(1, spring, 'Прог'). % Читается ли программирование весной первого курса.
   yes
?- course(1, spring, 'ТФКП'). % Читается ли ТФКП весной первого курса.
   no
?- course(1, spring, X). % Дисциплины весеннего семестра мервого курса
   X / МатАн
   X / ЛинАл
   X / ДиМат
   X / Прог
   X / АиСД
   X / ИнЯз
   X / С++
?- course(1, _, X). % Дисциплины первого курса
   X / Прог
   X / АлгСД
   X / АиСД
   X / АрхЭВМ
   X / МатАн
   X / ЛинАл
   X / ИнЯз
   X / МатАн
   X / ЛинАл
   X / ДиМат
   X / Прог
   X / АиСД
   X / ИнЯз
   X / С++
?- course(_, spring, X). % Дисциплины весеннего семестра
   X / МатАн
   X / ЛинАл
   X / ДиМат
   X / Прог
   X / АиСД
   X / ИнЯз
   X / С++
   X / Java
   X / АиСД
   X / ДиМат
   X / МатАн
   X / МатЛог
   X / ОС
   X / ТФКП
   X / ИнЯз
?- course(G, S, 'Прог'). % Когда читается программирование
   G / 2, S / fall
   G / 1, S / spring
?- course(1, fall, X), course(1, spring, X). % Дисциплины читаемые в обоих семестрах первого курса
   X / Прог
   X / АиСД
   X / МатАн
   X / ЛинАл
   X / ИнЯз
?- course(1, spring, X); course(2, fall, X). % Дисцилины, читаемые в третьем или четвертом семестрах.
   X / МатАн
   X / ЛинАл
   X / ДиМат
   X / Прог
   X / АиСД
   X / ИнЯз
   X / С++
   X / МатАн
   X / АиСд
   X / ДиМат
   X / ДифУр
   X / С++
   X / Web
   X / ИнЯз
   X / БЖД
*/


% Простое правило
fall(Y, C) :- course(Y, fall, C).
/*
?- fall(X, 'МатАн').
   X / 1
   X / 2
?- fall(X, 'Прог').
   X / 1
*/


% Составное правило (конъюнкция)
repeated(C) :- course(Y1, S1, C), course(Y2, S2, C), (Y1, S1) \= (Y2, S2).
/*
?- repeated(C).
   X / Прог
   X / АиСД
   X / АиСД
   X / МатАн
   X / МатАн
   X / МатАн
   X / ЛинАл
   X / ИнЯз
   X / ИнЯз
   X / ИнЯз
   X / МатАн
   X / МатАн
   X / МатАн
   X / ЛинАл
   X / ДиМат
   X / ДиМат
   X / Прог
   X / АиСД
   X / АиСД
   X / ИнЯз
   X / ИнЯз
   X / ИнЯз
   X / С++
   X / МатАн
   X / МатАн
   X / МатАн
   X / ДиМат
   X / ДиМат
   X / С++
   X / ИнЯз
   X / ИнЯз
   X / ИнЯз
   X / АиСД
   X / АиСД
   X / ДиМат
   X / ДиМат
   X / МатАн
   X / МатАн
   X / МатАн
   X / ИнЯз
   X / ИнЯз
   X / ИнЯз
?- setof(X, repeated(X), XS).
   XS / ['АиСД','ДиМат','ИнЯз','ЛинАл','МатАн','Прог','С++']
*/

% Составное правило (дизъюнкция)
next(Y, fall, Y, spring).
next(Y, spring, Y1, fall) :- Y1 is Y + 1.
/*
?- next(2, fall, Y, S).
   Y / 2  S / spring
?- next(1, spring, Y, S).
   Y / 2  S / fall
*/

repeated_next(Y, S, C) :- course(Y, S, C), next(Y, S, NY, NS), course(NY, NS, C).
/*
?- repeated_next(Y, S, 'МатАн').
   Y / 1  S / fall
   Y / 1  S / spring
   Y / 2  S / fall
?- repeated_next(Y, S, 'Прог').
   Y / 1  S / fall
*/

% Ориентированный граф
base(BY, BS, C, DY, DS, C) :- course(BY, BS, C), course(DY, DS, C), next(BY, BS, DY, DS).
base(1, spring, 'МатАн', 2, fall, 'ДифУр').
base(2, fall, 'МатАн', 2, spring, 'ТФКП').
base(1, spring, 'Прог', 2, fall, 'Web').
base(1, fall, 'АрхЭВМ', 1, spring, 'C++').
/*
?- base(1, fall, 'МатАн', Y, S, C).
   Y / 1  S / spring  C / МатАн
?- base(1, spring, 'МатАн', Y, S, C).
   Y / 2  S / fall  C / МатАн
   Y / 2  S / fall  C / ДифУр
*/

% Рекурсивный поиск достижимых вершин
base_all(BY, BS, BC, DY, DS, DC) :- base(BY, BS, BC, DY, DS, DC).
base_all(BY, BS, BC, DY, DS, DC) :- base(BY, BS, BC, IY, IS, IC), base_all(IY, IS, IC, DY, DS, DC).
/*
?- base_all(1, fall, 'МатАн', Y, S, C).
   Y / 1  S / spring  C / 'МатАн'
   Y / 2  S / fall    C / 'МатАн'
   Y / 2  S / fall    C / 'ДифУр'
   Y / 2  S / spring  C / 'МатАн'
   Y / 2  S / spring  C / 'ТФКП'
*/

% Последний семестр дисциплины (отрицание).
last(Y, S, C) :- course(Y, S, C), next(Y, S, NY, NS), \+ course(NY, NS, C).
/*
?- last(Y, S, 'МатАн').
   Y / 2  S / spring
?- last(1, fall, C).
   C / 'АрхЭВМ'
*/
