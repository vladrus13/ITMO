-- Выбираем хеш, так как дерево будет выполнять ту же роль при любой
-- конфигурации, но только медленнее
create unique index l_l_id on lecturers using hash (lecturerid);