-- psql (PostgreSQL) 
-- 12.8 
-- Ubuntu 12.8-0ubuntu0.20.04.1

-- В данной программе проверены такие случаи:
-- - Добавление оценки за курс, которого нет у студента
-- - Удаление курса из плана, который уже раздал оценки студентам

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
