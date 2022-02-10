-- ДЗ-5.3.5s. Информацию о студентах с :Mark по предмету
-- :LecturerId (sql)
-- ДЗ-5.3.6s. Информацию о студентах с :Mark по предмету
-- :LecturerName (sql)
-- ДЗ-7.1.4s. Удаление студентов с 3+ оценками (sql)
-- Связующий покрывающий индекс связующей таблички
create unique index m_s_id_c_id on marks using btree (studentid, courseid);

-- ДЗ-5.6.3s. StudentId имеющих оценки по всем предметам
-- :LecturerName (sql)
-- Связующий покрывающий индекс связующей таблички
create unique index m_c_id_s_id on marks using btree (courseid, studentid);

-- ДЗ-6.1.3r. Информация о студентах по :Mark и
-- :CourseId (rc-sql)
-- Связующий покрывающий индекс связующей таблички
create index marks_m_c_id on marks using btree (mark, courseid);

-- ДЗ-5.8.1s. SumMark по :StudentId (sql)
-- ДЗ-5.8.2s. SumMark для каждого студента (StudentName) (sql)
-- ДЗ-5.8.3s. SumMark студентов каждой группы (GroupName) (sql)
-- Связующий покрывающий индекс связующей таблички
create index marks_s_id_m on marks using btree (studentid, mark);