select g.GroupName, c.CourseName
  from Groups g, Courses c
  where not exists (
    select s.StudentId from Students s
    where
      s.GroupId = g.GroupId and
      not exists (
        select m.Mark from Marks m
          where
            s.StudentId = m.StudentId and
            c.CourseId = m.CourseId
      )
  )
