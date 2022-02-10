update Students set Marks = Marks + (
  select count(CourseId) from NewMarks m where Students.StudentId = m.StudentId
)
