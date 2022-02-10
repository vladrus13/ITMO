-- psql (PostgreSQL) 
-- 12.8 
-- Ubuntu 12.8-0ubuntu0.20.04.1

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

create trigger PreserverMarks after update on Marks 
  for each row execute procedure CompareAndSet();
