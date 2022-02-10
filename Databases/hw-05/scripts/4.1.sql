select StudentId, StudentName, GroupId from Students
except 
select StudentId, StudentName, GroupId from Marks natural join 
  Courses natural join 
  Students 
where CourseName = :CourseName
