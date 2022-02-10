update Students set Debts = (
  select count(distinct p.CourseId) 
    from Plan p 
    where
      Students.StudentId = :StudentId and
      Students.GroupId = p.GroupId and
      not exists (
        select distinct m.CourseId 
          from Marks m
          where
            m.StudentId = :StudentId and
            p.CourseId = m.CourseId
      )
) where StudentId = :StudentId
