select StudentId, StudentName, GroupId from Students s where s.GroupId in (
  select GroupId from Groups g where GroupName = :GroupName
);

