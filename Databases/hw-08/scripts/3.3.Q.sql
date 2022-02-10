-- Ищем все курсы, которые ведет преподаватель
select studentname
from students
where groupid = (select groupid from groups
  where groupname = 'M3435')
order by studentname;