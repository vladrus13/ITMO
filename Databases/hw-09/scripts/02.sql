create or replace function Reserve(UserId integer, Pass varchar(255), FlightId integer, SeatNo varchar(4))
    returns boolean
    language plpgsql
as
$$
begin
    -- Проверяет, действительный ли пользователь, и есть ли такой рейс, и не улетел ли он
    if (not checkUserAndFlight(UserId, Pass, FlightId)) then
        return false;
    end if;
    -- Проверяем, существует ли такое место, и не занято ли оно
    if (not getFreePlace(FlightId, SeatNo, true)) then
        return false;
    end if;
    -- Вставляем нужную строку
    insert into bookingsandsales(flightid, seatno, userid, casttype, time)
    values (Reserve.FlightId, Reserve.SeatNo, Reserve.UserId, 'book', now() + '3 day'::interval);
    return true;
end;
$$;

select *
from bookingsandsales;

select Reserve(1, 'password2', 1, '2A');

select Reserve(1, 'password1', 1, '02A');

select Reserve(1, 'password1', 1, '3A');

select Reserve(1, 'password1', 1, '1A');

select Reserve(1, 'password1', 1, '4A');

select Reserve(1, 'password1', 1, '5A');

select Reserve(1, 'password1', 1, '6A');

select *
from bookingsandsales;