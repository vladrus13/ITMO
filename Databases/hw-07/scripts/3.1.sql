update Students set Marks = (
  select count(CourseId) from Marks m where StudentId = :StudentId
) where StudentId = :StudentId
