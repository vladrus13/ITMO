create view Debts as
select StudentId, count(CourseId) as Debts from (
  select StudentId, CourseId from Students s natural join Plan p
  except
  select StudentId, CourseId from Marks m
) DeptsStudents
group by StudentId
having count(CourseId) > 0
