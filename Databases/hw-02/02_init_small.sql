insert into Groups(group_name) values ('M312'), ('M313'), ('M314');

insert into Students(email, phone, first_name, second_name, group_id) values
    ('abram_kurr@gmail.com', '+720475840237488', 'Abram', 'Kurr', 2),
    ('123alex_h@yandex.ru', '+9009896989', 'Alexandr', 'Hudakov', 2),
    ('maria_semenova@mail.com', '+79991502302', 'Maria', 'Semenova', 3);
    
insert into Teachers(email, first_name, second_name) values
    ('bb@yandex.ru', 'Ban', 'Bannov'),
    ('9264584659263947623943649237@mail.com', 'Vladimir', 'Palov');
    
insert into Subjects(name) values ('Lama-programming'), ('Effective programming');

insert into Grades(grades, student_id, subject_id) values
    (91, 1, 1),
    (94.1, 1, 2),
    (60, 2, 1),
    (60, 2, 2),
    (54, 3, 1),
    (0.01, 3, 2);
    
insert into Contract(teacher_id, subject_id) values
    (1, 1),
    (2, 2);
