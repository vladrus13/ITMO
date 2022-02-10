-- Выбираем btree, так как это связующий индекс
create index stude_g_n on students using btree(groupid, studentname);