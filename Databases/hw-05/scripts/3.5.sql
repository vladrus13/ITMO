select StudentId, StudentName, students.GroupId as GroupId
  from Students students
  natural join Marks m 
  inner join Plan p using (CourseId)
  where LecturerId = :LecturerId and Mark = :Mark
