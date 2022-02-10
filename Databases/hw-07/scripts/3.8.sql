update Students set Debts = (
  select count(distinct p.CourseId) 
    from Students s natural join Plan p 
    where
      Students.StudentId = s.StudentId and 
      not exists (
        select distinct m.CourseId 
          from Marks m
          where
            s.StudentId = m.StudentId and
            p.CourseId = m.CourseId
      )
), Marks = (
  select count(CourseId) from Marks m where Students.StudentId = m.StudentId
)
