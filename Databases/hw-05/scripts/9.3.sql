select GroupName, AvgMark from (
  Groups
  left join (
    select GroupId, cast(sum(UnderAvgMark) as double) / sum(CountAvgMark) as AvgMark from (
      select GroupId, UnderAvgMark, CountAvgMark from (
        Students
        natural join (
          select StudentId, sum(Mark) as UnderAvgMark, count(*) as CountAvgMark from Marks group by StudentId
        ) studentsMarks
      )
    ) groupsMarks
    group by GroupId
  ) groupsGroupedMarks
  on Groups.GroupId = GroupsGroupedMarks.GroupId
)
