select StudentName, CourseName
  from Students s, Courses c
  where
  exists (
    select distinct s.StudentId, p.CourseId 
      from Plan p
      where
        s.GroupId = p.GroupId and
        c.CourseId = p.CourseId and
        exists (
          select distinct s.StudentId, p.CourseId from Marks m
          where
            m.StudentId = s.StudentId and
            m.CourseId = p.CourseId and
            m.mark <= 2
        )
  )
