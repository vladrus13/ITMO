create table Groups(
    group_id   int         not null generated always as identity, constraint pk_group_id primary key(group_id),
    group_name varchar(64) not null
);

create table Students(
    student_id  int          not null generated always as identity, constraint pk_student_id primary key(student_id),
    email       varchar(129) not null,
    phone       varchar(16)  not null,
    first_name  varchar(64)  not null,
    second_name varchar(64)  not null,
    group_id    int          not null, constraint fk_group_id foreign key(group_id) references Groups(group_id)
);

create table Teachers(
    teacher_id    int          not null generated always as identity, constraint pk_teacher_id primary key(teacher_id),
    email         varchar(129) not null,
    first_name    varchar(64)  not null,
    second_name   varchar(64)  not null
);

create table Subjects(
    subject_id int         not null generated always as identity, constraint pk_subject_id primary key(subject_id),
    name       varchar(64) not null
);

create table Grades(
    grades     decimal(6, 3) not null,
    student_id int           not null, constraint fk_student_id foreign key(student_id) references Students(student_id),
    subject_id int           not null, constraint fk_subject_id foreign key(subject_id) references Subjects(subject_id)
);

create table Contract(
    teacher_id int not null, constraint fk_teacher_id foreign key(teacher_id) references Teachers(teacher_id),
    subject_id int not null, constraint fk_subject_id foreign key(subject_id) references Subjects(subject_id)
);
