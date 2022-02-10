select distinct StudentId from
  Marks 
  natural join Students 
  natural join Plan 
  natural join Lecturers
  where LecturerName = :LecturerName
