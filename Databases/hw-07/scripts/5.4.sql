create view StudentDebts as
select ls.StudentId, (
  select count(distinct p.CourseId) 
    from Students s natural join Plan p 
    where
      ls.StudentId = s.StudentId and 
      not exists (
        select distinct m.CourseId 
          from Marks m
          where
            s.StudentId = m.StudentId and
            p.CourseId = m.CourseId
      ) 
) as Debts 
from Students ls
