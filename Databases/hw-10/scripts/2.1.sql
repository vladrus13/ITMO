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

-- Используйте ее как минимум с уровнем транзакции read committed
-- Почти точная копия функции из предыдущего дз
create or replace function FreeSeats(FlightId int)
    returns table
            (
                seatNo varchar(4)
            )
    language plpgsql
as
$$
begin
    if
        (FlightId not in
         (select f.flightid from flights f where f.flightid = FreeSeats.FlightId and f.flighttime > now())) then
        commit;
        return;
    end if;
    return query select s.seatNo
                 from seats s
                 where s.planeid = (select f.planeid from flights f where f.flightid = FreeSeats.FlightId)
                   and s.seatno not in (select g.seatNo from getUsedPlaces(FreeSeats.FlightId) g);
end;
$$;

start transaction read only isolation level read committed;
select *
from FreeSeats(1);
commit;