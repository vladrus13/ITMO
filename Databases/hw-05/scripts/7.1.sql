select CourseId, GroupId 
  from Marks, Students
except
select CourseId, GroupId from (
  select CourseId, StudentId, GroupId from (
    select CourseId from Marks
    ) Studies,
    Students
    except
    select CourseId, StudentId, GroupId from Marks
      natural join Students
) nonMarks
