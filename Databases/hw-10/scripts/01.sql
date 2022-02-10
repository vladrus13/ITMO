-- Список мест, доступных для продажи и для бронирования
create
or replace function FreeSeats(FlightId int)
    returns table
            (
                seatNo varchar(4)
            )
    language plpgsql
as
$$
begin
    -- Проверяем, есть ли такой самолет вообще, и, если да, не улетел ли он еще
    if
(FlightId not in
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

start transaction read only isolation level read committed;
-- Мы осознаем, что грязное чтение - это проблема для нас, так как возможно, что добавится такой полет, а затем его rollbackнут
-- Однако понимаем, что данные мы читаем, значит, нам не грозит косое чтение
-- Понимаем, что если мы прочитали, что есть такой самолет и что он не улелел, а потом что то изменилось, то мы можем сказать, что первым выполнилась функция FreeSeats, то есть фантомное чтение и неповторяемое чтение нам не угрожает
-- То есть наш выбор - read committed