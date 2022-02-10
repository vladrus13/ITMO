select StudentName, SumMark from (
  Students
  left join (
    select StudentId, sum(Mark)
      as SumMark 
      from Marks 
      group by StudentId
  ) MarksIds
  on Students.StudentId = MarksIds.StudentId
)
