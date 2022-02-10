insert into Marks select StudentId, CourseId, Mark from NewMarks nm where not exists (
  select m.StudentId, m.CourseId from Marks m 
    where 
      nm.StudentId = m.StudentId and 
      nm.CourseId = m.CourseId 
)
