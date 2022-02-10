select StudentId, StudentName, GroupName
  from Students s, Groups g
  where 
    s.GroupId = g.GroupId and
    StudentId not in (
      select s.StudentId
        from Students s, Marks m
        where
          s.StudentId = m.StudentId and
          CourseId = :CourseId
      );
