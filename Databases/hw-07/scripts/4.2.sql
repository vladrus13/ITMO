update Marks as m set Mark = (
  select Mark from NewMarks nm 
    where 
      m.StudentId = nm.StudentId and
      m.CourseId = nm.CourseId and
      exists (
      select temp.StudentId, temp.CourseId from Marks temp
        where 
          temp.StudentId = nm.StudentId and 
          temp.CourseId = nm.CourseId 
      )
) where exists (
  select nm.StudentId, nm.CourseId from NewMarks nm 
  where 
    m.StudentId = nm.StudentId and
    m.CourseId = nm.CourseId
)
