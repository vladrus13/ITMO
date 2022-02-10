select StudentName, AvgMark from (
  Students
  left join (
    select StudentId, cast(sum(Mark) as double) / count(*) 
      as AvgMark 
      from Marks 
      group by StudentId
  ) MarksIds
  on Students.StudentId = MarksIds.StudentId
)
