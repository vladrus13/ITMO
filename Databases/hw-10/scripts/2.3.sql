-- Все функции являются почти полными копиями функций из прошлого дз

create or replace function isCorrectPassword(enter varchar(255), hash char(256), salt char(256))
    returns boolean
    language plpgsql
as
$$
begin
    return hash = md5(enter || salt);
end;
$$;

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

create or replace function getUsedPlaces(flightId int)
    returns table
            (
                seatNo varchar(4)
            )
    language plpgsql
as
$function$
begin
    return query (
        select b.seatno
        from bookingsandsales b
        where b.flightid = getUsedPlaces.flightId
          and ((b.casttype = 'sale') or (b.casttype = 'book' and b.time > now()))
    );
end;
$function$;

create or replace function getReservedPlaces(flightId integer, seatNo varchar(4))
    returns setof bookingsandsales
    language plpgsql
as
$function$
begin
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

create or replace function getFreePlace(flightId integer, seatNo varchar(4))
    returns boolean
    language plpgsql
as
$function$
begin
    if (getFreePlace.SeatNo not in (select s.seatno
                                    from seats s
                                    where planeid = (select f.planeid
                                                     from flights f
                                                     where f.flightid = getFreePlace.FlightId))) then
        return false;
    end if;
    if (getFreePlace.SeatNo in (select g.seatno from getusedplaces(getFreePlace.FlightId) g)) then
        return false;
    end if;
    return true;
end;
$function$;

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

-- следует запускать с read committed. Резервирует место за пользователем
create or replace function Reserve(UserId integer, Pass varchar(255), FlightId integer, SeatNo varchar(4))
    returns varchar(30)
    language plpgsql
as
$$
declare
    isSuccessful boolean default true;
begin
    if (not checkUserAndFlight(UserId, Pass, FlightId)) then
        return 'Имя пользователя неверно или полета не существует';
    end if;
    if (not getFreePlace(FlightId, SeatNo)) then
        return 'Место нельзя занять';
    end if;
    insert into bookingsandsales(flightid, seatno, userid, casttype, time)
    values (Reserve.FlightId, Reserve.SeatNo, Reserve.UserId, 'book', now() + '3 day'::interval)
    on conflict do nothing
    returning bookingsandsales.flightid is not null into isSuccessful;
    if (isSuccessful) then
        return 'Успешно забронировано';
    else
        return 'Место уже занято';
    end if;
end;
$$;

-- Следует запускать с read committed. Анонимно покупает место
create or replace function BuyFree(FlightId integer, SeatNo varchar(4))
    returns varchar(30)
    language plpgsql
as
$$
declare
    flight       integer;
    isSuccessful boolean default true;
begin
    flight := (select f.flightid from flights f where f.flightid = BuyFree.FlightId and f.flighttime > now());
    if (flight is null) then
        return 'Полета не существует';
    end if;
    if (not getFreePlace(FlightId, SeatNo)) then
        return 'Место уже занято';
    end if;
    insert into bookingsandsales(flightid, seatno, casttype, userid, time)
    values (BuyFree.FlightId, BuyFree.SeatNo, 'sale', null, null)
    on conflict do nothing
    returning bookingsandsales.flightid is not null into isSuccessful;
    if (isSuccessful) then
        return 'Успешно забронировано';
    else
        return 'Место уже занято';
    end if;
end;
$$;

-- Следует запускать с repeatable read. Продлевает регистрацию места
create or replace function ExtendReservation(UserId integer, Pass varchar(255), FlightId integer, SeatNo varchar(4))
    returns varchar(30)
    language plpgsql
as
$$
declare
    seatUserId   bookingsandsales;
    isSuccessful boolean;
begin
    if (not checkUserAndFlight(UserId, Pass, FlightId)) then
        return 'Имя пользователя неверно или полета не существует';
    end if;
    seatUserId := getReservedPlaces(ExtendReservation.FlightId, ExtendReservation.SeatNo);
    if (seatUserId is null or ExtendReservation.UserId != seatUserId.userid) then
        return 'Место не забронированно вами';
    end if;
    update bookingsandsales b
    set time = (seatUserId.time + '3 day'::interval)
    where b.flightid = ExtendReservation.FlightId
      and b.seatno = ExtendReservation.SeatNo
    returning b.flightid is not null into isSuccessful;
    if (isSuccessful) then
        return 'Успешно продлено';
    else
        return 'Ошибка поиска брони';
    end if;
end;
$$;

-- Следует запускать с repeatable read. Покупает зарезервированное место
create or replace function BuyReserved(UserId integer, Pass varchar(255), FlightId integer, SeatNo varchar(4))
    returns varchar(30)
    language plpgsql
as
$$
declare
    isSuccessful boolean;
begin
    if (not checkUserAndFlight(UserId, Pass, FlightId)) then
        return 'Имя пользователя неверно или полета не существует';
    end if;
    if (not exists(select *
                   from bookingsandsales b
                   where b.flightid = BuyReserved.FlightId
                     and b.seatno = BuyReserved.SeatNo
                     and b.userid = BuyReserved.UserId
                     and b.casttype = 'book'
                     and b.time >= now())) then
        return 'Данной брони не существует';
    end if;
    update bookingsandsales b
    set casttype = 'sale'
    where b.flightid = BuyReserved.FlightId
      and b.seatno = BuyReserved.SeatNo
    returning b.flightid is not null into isSuccessful;
    if (isSuccessful) then
        return 'Успешно куплено';
    else
        return 'Ошибка поиска брони';
    end if;
end;
$$;
-- Здесь подставлены данные (что бы успешно выполнялось), но можно подставить свои данные (userId = 1, password = 'password1', flightId = 1, seatNo = '2A'
start transaction read write isolation level read committed;
select *
from BuyFree(1, '2A');
commit;

start transaction read write isolation level read committed;
select *
from reserve(1, 'password1', 1, '3A');
commit;

start transaction read write isolation level read committed;
select *
from reserve(1, 'password2', 1, '3A');
commit;

start transaction read write isolation level repeatable read;
select *
from extendreservation(1, 'password1', 1, '3A');
commit;

start transaction read write isolation level repeatable read;
select *
from extendreservation(1, 'password2', 1, '4A');
commit;

start transaction read write isolation level repeatable read;
select *
from buyreserved(1, 'password1', 1, '3A');
commit;

start transaction read write isolation level repeatable read;
select *
from buyreserved(1, 'password2', 1, '3A');
commit;

start transaction read write isolation level repeatable read;
select *
from buyreserved(1, 'password1', 1, '4A');
commit;