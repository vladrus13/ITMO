\c postgres;

drop database temp;

create database temp;
\c temp;

create table Groups (
  GroupId integer primary key,
  GroupName varchar(255)
);

create table Students (
  StudentId integer primary key,
  StudentName varchar(255),
  GroupId integer references Groups (GroupId)
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
  GroupId integer references Groups (GroupId),
  CourseId integer references Courses (CourseId),
  LecturerId integer references Lecturers (LecturerId)
);

create table Marks (
  StudentId integer references Students (StudentId),
  CourseId integer references Courses (CourseId),
  Mark integer
);

insert into Groups (GroupId, GroupName)
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

-- Функция, которая отвечает за вывод в случае ошибок на добавление. 
-- Если хотите, что бы вывод был, то поставьте в return true.
-- Она начнет выводить, где же произошел конфликт при удалении

create function isRaise()
  returns boolean
  language plpgsql
  as $function$
    begin
    return true;
    end;
  $function$;

-- Функция, которая проверяет, что в данный момент все оценки
-- соответствуют плану. Мы будем вызывать ее не так часто, 
-- так как она проверяет все пары студентов и курсов

create function CheckExistsAll()
  returns boolean
  language plpgsql
  as $function$
    declare
      isBad boolean;
      bad record;
    begin
      for bad in
        select StudentId, CourseId from Marks
        except
        select StudentId, CourseId from Students natural join Plan
      loop
        if isRaise() 
          then raise notice 'Found pair of student and course with mark without in plan. Student - %, Course - %', bad.StudentId, bad.CourseId;
        end if;
        isBad = true;
      end loop;
      return isBad;
    end;
  $function$;
  
-- Функция, которая проверяет, что данный курс можно удалить
-- из плана так, что люди по по прежнему будут иметь оценки
-- только за те курсы, что у них есть

-- Другими словами, эта функция проверяет, что на данном курсе
-- никто не обучается

create function CheckExistsOnDeleteCourse(getCourseId integer)
  returns boolean
  language plpgsql
  as $function$
    declare
      isBad boolean;
      bad record;
    begin
      for bad in
        select StudentId, CourseId from Marks where CourseId = getCourseId
      loop
        if isRaise() 
          then raise notice 'Found pair on deleting course with existing mark on course with id - %, student with id - %', bad.CourseId, bad.StudentId;
        end if;
        isBad = true;
      end loop;
      return isBad;
    end;
  $function$;

-- Функция для триггера, которая проверяет, что курс можно удалить

create function TriggerExistsOnDeletingCourse()
  returns trigger
  language plpgsql
  as $function$
    begin
      if CheckExistsOnDeleteCourse(old.CourseId)
      then
        if isRaise() 
          then raise notice 'Failed delete course';
        end if;
        return null;
      else
        return old;
      end if;
    end;
  $function$;

-- Функция, которая проверяет, что оценку можно добавить

-- Другими словами, она проверяет, есть ли у студента тот курс,
-- на который мы пытаемся добавить оценку

create function CheckExistsCourseOnAddingMark(getStudentId integer, getCourseId integer)
  returns boolean
  language plpgsql
  as $function$
    declare
      isBad boolean;
    begin
      if (not exists (select StudentId, CourseId from Students natural join Plan where CourseId = getCourseId and StudentId = getStudentId))
      then
        isBad = true;
        if isRaise() 
          then raise notice 'Found that course not exists on adding mark on course with id - %, student with id - %', getCourseId, getStudentId;
        end if;
      else
        isBad = false;
      end if;
      return isBad;
    end;
  $function$;
  
-- Функция для триггера, которая проверяет, что оценку можно добавить
  
create function TriggerExistsCourseOnAddingMark()
  returns trigger
  language plpgsql
  as $function$
    begin
      if CheckExistsCourseOnAddingMark(new.StudentId, new.CourseId)
      then
        if isRaise() 
          then raise notice 'Failed adding';
        end if;
        return null;
      else
        return old;
      end if;
    end;
  $function$;

-- Триггер для того, что бы не удалить курс, на котором студенты уже получили оценки

create trigger onDeletingCourse before delete on Plan
  for each row execute procedure TriggerExistsOnDeletingCourse();

-- Триггер для того, что бы не добавить оценку за курс, на котором студент не учится

create trigger onInsertMark before insert or update of StudentId, CourseId on Marks
  for each row execute procedure TriggerExistsCourseOnAddingMark();

-- Проверка в самом начале, что табличка соответствует требованиям. 
-- Внимание! Она всего лишь выводит конфликты, но не решает их
-- Вывод будет только при isRaise() = true

do $$ begin
  perform CheckExistsAll();
end $$;

select * from Plan where GroupId = 1 and CourseId = 1; 

delete from Plan where GroupId = 1 and CourseId = 1;

select * from Plan where GroupId = 1 and CourseId = 1;

select * from Marks;

insert into Marks (StudentId, CourseId, Mark) values (1, 5, 4);

select * from Marks;

\c postgres;

drop database temp;
