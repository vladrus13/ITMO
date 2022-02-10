select GroupName, CourseName
  from Marks natural join Courses, Students natural join Groups
except
select GroupName, CourseName from (
  select CourseId, StudentId, GroupId, GroupName, CourseName from (
    select CourseId, CourseName from Marks natural join Courses
    ) Studies,
    Students natural join Groups
    except
    select CourseId, StudentId, GroupId, GroupName, CourseName from Marks
      natural join Students natural join Groups natural join Courses
) nonMarks
