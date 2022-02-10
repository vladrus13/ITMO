select StudentId, StudentName, students.GroupId as GroupId
  from Students students
  inner join Marks using (StudentId) 
  inner join Plan using (CourseId)
  inner join Lecturers using (LecturerId)
  where LecturerName = :LecturerName and Mark = :Mark
