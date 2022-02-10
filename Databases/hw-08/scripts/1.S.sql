-- ДЗ-5.1.1s. Информация о студентах по :StudentId (sql)
-- ДЗ-5.2.1s. Полная информация о студентах по :StudentId (sql)
-- ДЗ-5.8.1s. SumMark по :StudentId (sql)
-- Выбираем хеш, так как дерево будет выполнять ту же роль при
-- любой конфигурации, но только медленнее
create unique index students_s_id on Students using hash (studentid);

-- ДЗ-5.1.2s. Информация о студентах по :StudentName (sql)
-- ДЗ-5.2.2s. Полная информация о студентах по :StudentName (sql)
-- ДЗ-5.9.2s. AvgMark для каждого студента (StudentName) (sql)
-- Выбираем хеш, так как дерево будет очень медленное в этом случае,
-- просто ради получения полного множества столбцов
create index students_s_name on students using hash (studentname);

-- ДЗ-5.6.1s. StudentId имеющих хотя бы одну оценку у
-- :LecturerName (sql)
-- ДЗ-5.6.2s. StudentId не имеющих оценок у :LecturerName (sql)
-- ДЗ-5.6.3s. StudentId имеющих оценки по всем предметам
-- :LecturerName (sql)
-- Связующий покрывающий индекс. Эта таблица может исползоваться как
-- связующая
create unique index sgi on students using btree (groupid, studentid);