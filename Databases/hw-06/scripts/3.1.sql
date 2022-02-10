select StudentId, CourseId
  from Students s, Plan p
  where s.GroupId = p.GroupId
union
select StudentId, CourseId from Marks
