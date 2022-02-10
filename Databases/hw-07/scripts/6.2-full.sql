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
  (1, 'M3435');

insert into Students (StudentId, StudentName, GroupId)
  values
  (1, 'Ivanov I.I.', 1),
  (2, 'Petrov P.P.', 1);

insert into Courses (CourseId, CourseName)
  values
  (1, 'DBMS'),
  (2, 'Project managing');

insert into Marks (StudentId, CourseId, Mark)
  values
  (1, 1, 5),
  (2, 1, 4);

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

-- Проверяет, что все студенты группы имеют те же оценки, 
-- что и любой в их группе
create function IsCorrect()
  returns boolean
  language plpgsql
  as $function$
    declare
      isCool boolean;
      bad record;
    begin
      isCool = true;
      for bad in
        select StudentId, CourseId from Students natural join (select distinct GroupId, CourseId from Students natural join Marks) GroupsMarks
        except
        select distinct StudentId, CourseId from Marks natural join Students
      loop
        if isRaise()
        then
          raise notice 'Student with id = % doesnt have mark on course on with id = %, but someone in his group does', bad.StudentId, bad.CourseId;
          isCool = false;
        end if;
      end loop;
      return isCool;
    end;
  $function$;

-- Функция триггера для проверки на то, что все студенты 
-- группы имеют те же оценки, что и любой в их группе
create function TriggerIsCorrect()
  returns trigger
  language plpgsql
  as $function$
    begin
      if (not IsCorrect())
      then
        raise exception 'Failed adding';
      end if;
      return null;
    end;
  $function$;

-- Триггер для проверки на то, что все студенты группы имеют
-- те же оценки, что и любой в их группе. 
-- Добавление или изменение оценки

create trigger onMark after insert or update of StudentId, CourseId or delete on Marks
  for each statement execute procedure TriggerIsCorrect();
  
-- Триггер для проверки на то, что все студенты группы имеют
-- те же оценки, что и любой в их группе
-- Перевод или добавление студента

create trigger onStudent after insert or update of StudentId, GroupId on Students
  for each statement execute procedure TriggerIsCorrect();
  
-- Проверка в самом начале, что табличка соответствует требованиям. 
-- Внимание! Она всего лишь выводит конфликты, но не решает их
-- Вывод будет только при isRaise() = true

do $$ begin
  perform isCorrect();
end $$;

insert into Marks (StudentId, CourseId, Mark) values (2, 2, 4);
insert into Students (StudentId, StudentName, GroupId) values (3, 'ABBB', 1);

\c postgres;

drop database temp;
