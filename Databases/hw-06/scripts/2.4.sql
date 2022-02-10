select distinct StudentId, StudentName, GroupName
  from Students s, Plan p, Groups g
  where 
    s.GroupId = g.GroupId and
    g.GroupId = p.GroupId and
    CourseId = :CourseId and
    StudentId not in (
      select s.StudentId
        from Marks m, Students s
        where
          s.StudentId = m.StudentId and
          CourseId = :CourseId
    );
