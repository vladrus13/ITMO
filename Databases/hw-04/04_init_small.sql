insert into Groups(group_id, group_name) values 
    (1, 'M312'), 
    (2, 'M313'), 
    (3, 'M314');

insert into Students(student_id, name, group_id) values
    (1, 'Abram Kurr', 1),
    (2, 'Alexandr Hudakov', 1),
    (3, 'Maria Semenova', 2);
  
insert into Courses(course_name) values ('Lama-programming'), ('Effective programming');
  
insert into Lectures(lecturer_name) values
    ('Ban Bannov'),
    ('Vladimir Palov');

insert into Marks(mark, student_id, course_id, lecturer_id) values
    (91, 1, 1, 1),
    (94.1, 1, 2, 2),
    (60, 2, 1, 1),
    (60, 2, 2, 2),
    (54, 3, 1, 1),
    (0.01, 3, 2, 2);
