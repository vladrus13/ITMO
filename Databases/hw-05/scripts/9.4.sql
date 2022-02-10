select GroupName, AvgAvgMark from (
  Groups
  left join (
    select GroupId, cast(sum(UnderAvgMark) as double) / count(*) as AvgAvgMark from (
      select GroupId, UnderAvgMark from (
        Students
        natural join (
          select StudentId, cast(sum(Mark) as double) / count(*) as UnderAvgMark from Marks group by StudentId
        ) studentsMarks
      )
    ) groupsMarks
    group by GroupId
  ) groupsGroupedMarks
  on Groups.GroupId = GroupsGroupedMarks.GroupId
)
