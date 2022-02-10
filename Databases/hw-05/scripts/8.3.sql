select GroupName, SumMark from (
  Groups
  left join (
    select GroupId, sum(UnderSum) as SumMark from (
      select GroupId, UnderSum from (
        Students
        natural join (
          select StudentId, sum(Mark) as UnderSum from Marks group by StudentId
        ) studentsMarks
      )
    ) GroupsMarks
    group by GroupId
  ) GroupsGroupedMarks
  on Groups.GroupId = GroupsGroupedMarks.GroupId
)
