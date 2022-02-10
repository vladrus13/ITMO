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

start transaction read write isolation level repeatable read;
-- Рассмотрим проблемы.
-- Косая запись не является проблемой, так как у нас одна табличка, от нее никто не зависит.
-- Фантомная запись не является проблемой, так она может только удалить строки, но в этом случае мы только изменим ничего
-- Однако неповторяющее чтение проблема, так как мы могли посмотреть что билет принадлежит нужному пользователю, затем билет истек, его забронировал следующий человек, и мы успешно покупаем его (что является проблемой)
-- Наш выбор - repeatable read