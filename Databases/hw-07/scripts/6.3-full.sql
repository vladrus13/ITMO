drop database temp;

create database temp;
\c temp;

create table Groupss (
  GroupId integer primary key,
  GroupName varchar(255)
);

create table Students (
  StudentId integer primary key,
  StudentName varchar(255),
  GroupId integer references Groupss (GroupId)
);

create table Courses (
  CourseId integer primary key,
  CourseName varchar(255)
);

create table Lecturers (
  LecturerId integer primary key,
  LecturerName varchar(255)
);

create table Plan (
  GroupId integer references Groupss (GroupId),
  CourseId integer references Courses (CourseId),
  LecturerId integer references Lecturers (LecturerId)
);

create table Marks (
  StudentId integer references Students (StudentId),
  CourseId integer references Courses (CourseId),
  Mark integer
);

insert into Groupss (GroupId, GroupName)
  values
  (1, 'M3435'),
  (2, 'M3439'),
  (3, 'M3238'),
  (4, 'M3239');

insert into Students (StudentId, StudentName, GroupId)
  values
  (1, 'Ivanov I.I.', 1),
  (2, 'Petrov P.P.', 1),
  (3, 'Petrov P.P.', 2),
  (4, 'Cyderov C.C.', 2),
  (5, 'Unknown U.U.', 3),
  (6, 'Unnamed U.N.', 4),
  (7, 'X X.X.', 2),
  (8, 'Y Y.Y.', 2);

insert into Courses (CourseId, CourseName)
  values
  (1, 'DBMS'),
  (2, 'Project managing'),
  (3, 'Software design'),
  (4, 'Information Theory'),
  (6, 'Mathematical Analysis'),
  (7, 'Java Tech');

insert into Lecturers (LecturerId, LecturerName)
  values
  (1, 'Korneev G.A.'),
  (2, 'Yurchenko A.O.'),
  (3, 'Kuznetsova E.M.'),
  (4, 'Kirakozoff A.K.'),
  (5, 'Jakuba N.V.'),
  (6, 'Trofimyuk G.A.'),
  (7, 'Kudryashov B.D.'),
  (8, 'Kohas K.P.');

insert into Plan (GroupId, CourseId, LecturerId)
  values
  (1, 1, 2),
  (2, 1, 1),
  (1, 2, 3),
  (1, 3, 4),
  (2, 3, 4),
  (1, 4, 5),
  (2, 4, 6),
  (1, 4, 7),
  (2, 4, 7),
  (4, 6, 8),
  (1, 7, 1),
  (2, 7, 1),
  (3, 7, 1),
  (4, 7, 1);

insert into Marks (StudentId, CourseId, Mark)
  values
  (1, 1, 5),
  (2, 1, 4),
  (3, 1, 3),
  (2, 2, 3),
  (3, 2, 4),
  (4, 2, 5),
  (7, 1, 5),
  (8, 1, 5),
  (7, 7, 5),
  (8, 7, 5),
  (5, 7, 5),
  (6, 7, 5),
  (3, 3, 3);

create function CompareAndSet()
  returns trigger
  language plpgsql
  as $function$
    begin
      if new.Mark > old.Mark then
        return new;
      else
        return null;
      end if;
    end;
  $function$;

create trigger PreserverMarks before update on Marks 
  for each row execute procedure CompareAndSet();

select * from Marks;

update Marks set mark = mark + 1 where StudentId = 1 and courseId = 1;

select * from Marks;

update Marks set mark = mark - 1 where StudentId = 1 and CourseId = 1;

select * from Marks;

\c postgres;

drop database temp;
