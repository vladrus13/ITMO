-- ДЗ-5.3.4s. Информацию о студентах с :Mark по предмету
-- :LecturerName (sql)
-- ДЗ-5.6.2s. StudentId не имеющих оценок у :LecturerName
-- (sql)
-- ДЗ-5.6.3s. StudentId имеющих оценки по всем предметам
-- :LecturerName (sql)
-- Выбираем хеш, так как дерево будет очень медленное в этом
-- случае, просто ради получения полного множества столбцов
create index l_l_name on lecturers using hash (lecturername);