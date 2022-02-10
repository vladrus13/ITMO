select StudentId from Marks
  left join Plan on Marks.CourseId = Plan.CourseId
except
select StudentId from (
  select StudentId, CourseId from (
    select LecturerId, LecturerName, GroupId, CourseId from Lecturers
      natural join Plan
      where LecturerName = :LecturerName
  ) LearningPlan, (
    select StudentId, Mark from Marks
      left join Plan on Marks.CourseId = Plan.CourseId
  ) Studing
  except
  select StudentId, Plan.CourseId from Marks
    left join Plan on Plan.CourseId = Marks.CourseId
) OutStyding
