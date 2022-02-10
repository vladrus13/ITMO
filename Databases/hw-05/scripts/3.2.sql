select StudentId, StudentName, GroupId from Marks 
natural join Courses 
natural join Students 
where Mark = :Mark and CourseName = :CourseName
