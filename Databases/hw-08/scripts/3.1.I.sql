-- Поиск по подстроке. Добавим studentId, так как он нужен при
-- запросе
create index s_n_i on students using btree (studentname, studentid);