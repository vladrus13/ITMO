select distinct StudentId, StudentName, GroupName
  from Students s, Courses c, Plan p, Groups g
  where 
    s.GroupId = g.GroupId and
    p.CourseId = c.CourseId and
    g.GroupId = p.GroupId and
    CourseName = :CourseName and
    StudentId not in (
      select s.StudentId
        from Marks m, Students s, Courses c
        where
          m.CourseId = c.CourseId and
          s.StudentId = m.StudentId and
          CourseName = :CourseName
    );
