select StudentId, StudentName, GroupId from Marks 
natural join Students 
natural join Plan 
natural join Lecturers 
where Mark = :Mark and LecturerName = :LecturerName
