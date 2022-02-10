select distinct s.StudentId, s.StudentName, s.GroupId
  from Students s, Marks m, Courses c
  where 
    s.StudentId = m.StudentId and
    m.CourseId = c.CourseId and
    Mark = :Mark and
    CourseName = :CourseName
