create view AllMarks as
select s.StudentId, 
  (select count(CourseId) from Marks m where StudentId = s.StudentId) + 
  (select count(CourseId) from NewMarks nm where StudentId = s.StudentId) 
  as Marks 
from Students s
