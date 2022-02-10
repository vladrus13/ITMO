select distinct s.StudentId
  from Students s, Marks m, Plan p, Lecturers l
  where 
    s.StudentId = m.StudentId and
    p.LecturerId = l.LecturerId and
    s.GroupId = p.GroupId and
    p.CourseId = m.CourseId and
    LecturerName = :LecturerName
