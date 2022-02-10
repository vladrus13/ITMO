-- Выдать все отношения типа студеннт-преподаватель этого
-- студента по какому либо предмету
select studentname, lecturername
from students
         natural join groups
         natural join plan
         natural join lecturers