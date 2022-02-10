update Students set Marks = (
  select count(distinct CourseId) 
    from Marks m 
    where Students.StudentId = m.StudentId
)
