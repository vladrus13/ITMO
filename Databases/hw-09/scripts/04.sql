create or replace function BuyFree(FlightId integer, SeatNo varchar(4))
    returns boolean
    language plpgsql
as
$$
declare
    flight integer;
begin
    -- Смотрим, не улетел ли самолет
    flight := (select f.flightid from flights f where f.flightid = BuyFree.FlightId and f.flighttime > now());
    if (flight is null) then
        return false;
    end if;
    -- Проверяем, существует ли такое место, и не занято ли оно
    if (not getFreePlace(FlightId, SeatNo, true)) then
        return false;
    end if;
    -- Вставляем покупку
    insert into bookingsandsales(flightid, seatno, casttype, userid, time)
    values (BuyFree.FlightId, BuyFree.SeatNo, 'sale', null, null);
    return true;
end;
$$;

select * from bookingsandsales;

select BuyFree(1, '1A');

select BuyFree(1, '2A');

select BuyFree(3, '1A');

select * from bookingsandsales;