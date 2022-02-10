create or replace function BuyReserved(UserId integer, Pass varchar(255), FlightId integer, SeatNo varchar(4))
    returns boolean
    language plpgsql
as
$$
begin
    -- Проверяем, действительный ли пользоватль, и не улетел ли рейс (и существует ли он вообще)
    if (not checkUserAndFlight(UserId, Pass, FlightId)) then
        return false;
    end if;
    -- Смотрим, есть ли вообще такая бронь, проверяем, кто ее владелец, и не истекла ли она
    if (not exists(select *
                   from bookingsandsales b
                   where b.flightid = BuyReserved.FlightId
                     and b.seatno = BuyReserved.SeatNo
                     and b.userid = BuyReserved.UserId
                     and b.casttype = 'book'
                     and b.time >= now())) then
        return false;
    end if;
    -- Обновляем бронь как покупку. Можем оставить userId
    update bookingsandsales b
    set casttype = 'sale'
    where b.flightid = BuyReserved.FlightId
      and b.seatno = BuyReserved.SeatNo;
    return true;
end;
$$;

select *
from bookingsandsales;

select Reserve(1, 'password1', 1, '2A');

select Reserve(1, 'password1', 1, '3A');

select Reserve(1, 'password1', 1, '4A');

select Reserve(1, 'password1', 1, '5A');

select Reserve(1, 'password1', 1, '6A');

select BuyReserved(1, 'password1', 1, '1A');

select BuyReserved(1, 'password1', 1, '7A');

select BuyReserved(1, 'password1', 1, '2A');

select BuyReserved(1, 'password2', 1, '2A');

select BuyReserved(1, 'password1', 1, '20A');

select BuyReserved(1, 'password1', 10, '1A');

select *
from bookingsandsales;