delete from Students where StudentId not in (
  select StudentId 
    from Students natural join Plan natural left join Marks
    where Mark is null
    group by StudentId
    having count(distinct CourseId) > 2
)
