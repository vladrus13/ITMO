delete from Students where StudentId not in 
  (select StudentId from Marks)
