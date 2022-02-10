select StudentName, CourseName
  from Students s, Courses c
  where exists (
    select s.StudentId, CourseId from Plan p
      where 
        s.GroupId = p.GroupId and
        p.CourseId = c.CourseId
    union
    select StudentId, CourseId from Marks m
    where
      m.StudentId = s.StudentId and
      m.CourseId = c.CourseId
  )
