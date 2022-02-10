select StudentName, CourseName from
  (select distinct Students.StudentId, Courses.CourseId from Students
    natural join Plan
    natural join Courses
    left join Marks on Marks.StudentId = Students.StudentId and Marks.CourseId = Courses.CourseId
  where Mark is null or (Mark <> 4 and Mark <> 5)
  ) Studies
  natural join Students
  natural join Courses