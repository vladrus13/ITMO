select s.StudentId
  from Students s
  where s.StudentId not in (
    select m.StudentId
    from Plan p, Marks m, Lecturers l
    where
      s.GroupId = p.GroupId and
      m.CourseId = p.CourseId and
      LecturerName = :LecturerName and
      p.LecturerId = l.LecturerId
  )
