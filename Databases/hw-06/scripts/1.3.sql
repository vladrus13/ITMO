select distinct s.StudentId, s.StudentName, s.GroupId
  from Students s, Marks m
  where 
    s.StudentId = m.StudentId and
    CourseId = CourseId and
    Mark = :Mark and
    CourseId = :CourseId
