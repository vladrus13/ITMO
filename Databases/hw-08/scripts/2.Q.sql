select avg(m.Mark)
from Students s
         natural join Marks m
where s.groupid = (select g.groupid
    from groups g where g.groupname = :groupname)
  and m.courseid = (select c.courseid
    from courses c where c.coursename = :coursename);