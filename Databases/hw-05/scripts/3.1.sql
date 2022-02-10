select StudentId, StudentName, GroupId from Marks 
natural join Students 
where Mark = :Mark and CourseId = :CourseId
