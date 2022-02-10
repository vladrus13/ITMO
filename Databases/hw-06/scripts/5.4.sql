select s.StudentId
  from Students s
  where not exists (
    select s.StudentId
    from Plan p, Lecturers l
    where
      p.LecturerId = l.LecturerId and
      p.GroupId = s.GroupId and
      LecturerName = :LecturerName and
      not exists (
        select m.Mark from Marks m
        where
          s.StudentId = m.StudentId and
          m.CourseId = p.CourseId
      )
  )
