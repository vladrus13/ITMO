-- ДЗ-5.3.3s. Информацию о студентах с :Mark по предмету
-- :LecturerId (sql)
-- Связующий покрывающий индекс связующей таблички
create index plan_g_id_c_id on plan using btree (groupid, courseid);

-- ДЗ-5.3.4s. Информацию о студентах с :Mark по предмету
-- :LecturerName (sql)
-- Связующий покрывающий индекс связующей таблички
create index p_l_id_g_id on plan using btree (lecturerid, groupid);

-- ДЗ-5.3.4s. Информацию о студентах с :Mark по предмету
-- :LecturerName (sql)
-- Связующий покрывающий индекс связующей таблички
create index plan_c_id_g_id on plan using btree (courseid, groupid);

-- ДЗ-5.3.5s. Информацию о студентах с :Mark по предмету
-- :LecturerId (sql)
-- Связующий покрывающий индекс связующей таблички
create index p_l_id_c_id on plan using btree (lecturerid, courseid);