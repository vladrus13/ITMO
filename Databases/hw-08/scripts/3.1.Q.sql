-- Находит всех людей с фамилией Иванов. На самом деле там могла
-- быть любая другая фамилия, но для удобства взят пример
select studentid, studentname
from students
where studentname like 'Ivanov %';