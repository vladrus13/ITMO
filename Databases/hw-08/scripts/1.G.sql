-- ДЗ-5.2.1s. Полная информация о студентах по :StudentId (sql)
-- ДЗ-5.2.2s. Полная информация о студентах по :StudentName (sql)
-- ДЗ-5.5.1s. ФИО студента и названия предметов которые у него
-- есть по плану (sql)
-- Выбираем хеш, так как дерево будет выполнять ту же роль при любой
-- конфигурации, но только медленнее
create unique index groups_group_id on groups using hash (groupid);

-- ДЗ-5.8.3s. SumMark студентов каждой группы (GroupName) (sql)
-- ДЗ-5.9.3s. AvgMark каждой группы (GroupName) (sql)
-- ДЗ-5.9.4s. AvgAvgMark студентов каждой группы (GroupName) (sql)
-- Выбираем хеш, так как дерево будет очень медленное в этом случае,
-- просто ради получения полного множества столбцов
create unique index groups_group_name on groups using hash (groupname);