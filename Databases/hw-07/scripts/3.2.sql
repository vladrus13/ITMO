update Students set Marks = (
  select count(CourseId) from Marks m where Students.StudentId = m.StudentId
)
