merge into Marks mm
using NewMarks nm
on
  mm.StudentId = nm.StudentId and
  mm.CourseId = nm.CourseId
when matched and mm.Mark < nm.Mark then update set Mark = nm.Mark
when not matched then insert (StudentId, CourseId, Mark) values (nm.StudentId, nm.CourseId, nm.Mark)
