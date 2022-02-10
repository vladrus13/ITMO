-- Список мест, доступных для продажи и для бронирования
create or replace function FreeSeats(FlightId int)
    returns table
            (
                seatNo varchar(4)
            )
    language plpgsql
as
$$
begin
    -- Проверяем, есть ли такой самолет вообще, и, если да, не улетел ли он еще
    if (FlightId not in
        (select f.flightid from flights f where f.flightid = FreeSeats.FlightId and f.flighttime > now())) then
        return;
    end if;
    -- Смотрим все места, самолет которых есть самолет рейса, и что такого места нет в занятых
    return query select s.seatNo
                 from seats s
                 where s.planeid = (select f.planeid from flights f where f.flightid = FreeSeats.FlightId)
                   and s.seatno not in (select g.seatNo from getUsedPlaces(FreeSeats.FlightId, false) g);
end;
$$;

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '1B', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '0B', null);

select FreeSeats(1);

select FreeSeats(2);

