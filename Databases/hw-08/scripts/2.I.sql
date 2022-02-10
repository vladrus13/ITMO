-- Выбираем хеш, так как дерево будет очень медленное
-- в этом случае, просто ради получения полного множества столбцов
create unique index groups_id on groups using hash (groupname);

-- Выбираем хеш, так как дерево будет очень медленное
-- в этом случае, просто ради получения полного множества столбцов
create index courses_id on courses using hash (coursename);

-- Выбираем хеш, так как дерево будет очень медленное
-- в этом случае, просто ради получения полного множества столбцов
create index marks_mark on marks using hash (studentid);