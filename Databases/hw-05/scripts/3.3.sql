select StudentId, StudentName, GroupId from Marks 
natural join Plan 
natural join Students 
where Mark = :Mark and LecturerId = :LecturerId
