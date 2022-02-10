-- Удаляет все истекшие брони. Должно быть вызвано только в тех функциях, что имеют права записи на табличку билетов
create or replace procedure updateBooked()
    language plpgsql
as
$function$
declare
    now timestamp default now();
begin
    delete from bookingsandsales b where b.CastType = 'book' and b.time < now;
end;
$function$;

-- Получаем все места, которые были забронированы или куплены
-- isWritable - имеем ли мы доступ на запись в табличку билетов
create or replace function getUsedPlaces(flightId int, isWritable boolean)
    returns table
            (
                seatNo varchar(4)
            )
    language plpgsql
as
$function$
begin
    if (isWritable) then
        call updateBooked();
    end if;
    return query (
        select b.seatno
        from bookingsandsales b
        where b.flightid = getUsedPlaces.flightId
          and ((b.casttype = 'sale') or (b.casttype = 'book' and b.time > now()))
    );
end;
$function$;

-- Получаем запись о бронировании на место на определенный рейс
-- isWritable - имеем ли мы доступ на запись в табличку билетов
create or replace function getReservedPlaces(flightId integer, seatNo varchar(4), isWritable boolean)
    returns setof bookingsandsales
    language plpgsql
as
$function$
begin
    if (isWritable) then
        call updateBooked();
    end if;
    return query (
        select *
        from bookingsandsales b
        where b.flightid = getReservedPlaces.flightId
          and b.seatno = getReservedPlaces.seatNo
          and b.casttype = 'book'
          and b.time > now()
    );
end;
$function$;

-- Свободное ли данное место
-- isWritable - имеем ли мы доступ на запись в табличку билетов
create or replace function getFreePlace(flightId integer, seatNo varchar(4), isWritable boolean)
    returns boolean
    language plpgsql
as
$function$
begin
    if (isWritable) then
        call updateBooked();
    end if;
    -- Если оказалось, что такого места вовсе не могло существовать в этом самолете
    if (getFreePlace.SeatNo not in (select s.seatno
                               from seats s
                               where planeid = (select f.planeid from flights f where f.flightid = getFreePlace.FlightId))) then
        return false;
    end if;
    -- Если оно занято
    if (getFreePlace.SeatNo in (select g.seatno from getusedplaces(getFreePlace.FlightId, isWritable) g)) then
        return false;
    end if;
    return true;
end;
$function$;

-- Смотрим, правда ли пароль корректен
create or replace function isCorrectPassword(enter varchar(255), hash char(256), salt char(256))
    returns boolean
    language plpgsql
as
$$
begin
    return hash = md5(enter || salt);
end;
$$;

-- Проверяем, подходит ли пароль пользователю
create or replace function checkUser(UserId integer, Pass varchar(255))
    returns boolean
    language plpgsql
as
$$
declare
    userHash char(256);
    userSalt char(256);
begin
    select u.passwordhash, u.passwordsalt
    into userHash, userSalt
    from users u
    where u.userid = checkUser.UserId;
    if (userHash is null or (not isCorrectPassword(checkUser.Pass, userHash, userSalt))) then
        return false;
    end if;
    return true;
end;
$$;
end;

-- Проверяем, подходит ли пользователю пароль и правда ли, что такой рейс существует и он не улетел
create or replace function checkUserAndFlight(UserId integer, Pass varchar(255), FlightId integer)
    returns boolean
    language plpgsql
as
$$
begin
    return exists(select * from flights f where f.flightid = checkUserAndFlight.FlightId and f.flighttime > now()) and
           checkUser(UserId, Pass);
end;
$$;

-- Дополняет строку из <= 4 символов нулями
-- Например: 1A -> 001A
create or replace function rowFillGaps(row_name varchar(4))
    returns char(4)
    language plpgsql
as
$function$
declare
    row_length    integer;
    no_row_length integer;
    nulls         varchar(2) default '';
begin
    row_length := length(row_name);
    no_row_length := 4 - row_length;
    for i in 1..no_row_length
        loop
            nulls := nulls || '0';
        end loop;
    return nulls || row_name;
end;
$function$;

-- Убирает все ведущие нули ИЗ ЧИСЛА номера места
-- Пример: 001A -> 1A, 000A -> 0A, 101A -> 101A
create or replace function dropGaps(row_name char(4))
    returns varchar(4)
    language plpgsql
as
$function$
begin
    for i in 0..3
        loop
            if ascii(substring(row_name, i)) != ascii('0') then
                return substring(row_name, i);
            end if;
        end loop;
    return substring(row_name, 3);
end;
$function$;