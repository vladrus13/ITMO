create or replace procedure CompressSeats(FlightId integer)
    language plpgsql
as
$$
declare
    -- Создаем курсор для всех мест. Он будет поочередный. Именно отсюда будем черпать информацию о том, какие места первее
    seats_cursor cursor for
        select rowfillgaps(s.seatno) as seatNo
        from seats s
        where s.planeid = (select f.planeId from flights f where f.flightid = CompressSeats.FlightId)
        order by seatno;
    sailed_cursor cursor for
        select s.seatno
        from bookingsandsales s
        where s.casttype = 'sale'
          and s.flightid = CompressSeats.FlightId;
    booked_cursor cursor for
        select s.seatno
        from bookingsandsales s
        where s.casttype = 'book'
          and s.flightid = CompressSeats.FlightId
          and s.time > now();
    temp char(4);
    it   record;
begin
    -- Создаем табличку, которая будет хранить, какие места будут перемешаны с какими
    -- Нужна для того, что бы не редактировать сырые данные. Что бы случайно не перезаписать чего не надо
    -- Что бы мы изменили все за один запрос.
    drop table if exists Movement;
    create temporary table Movement
    (
        seatNo_from char(4),
        seatNo_to   char(4),
        constraint movement_pk primary key (seatNo_from)
    );
    open seats_cursor;
    for it in sailed_cursor
        loop
            fetch seats_cursor into temp;
            insert into Movement(seatNo_from, seatNo_to) values (it.seatno, temp);
        end loop;
    for it in booked_cursor
        loop
            fetch seats_cursor into temp;
            insert into Movement(seatNo_from, seatNo_to) values (it.seatno, temp);
        end loop;
    update bookingsandsales b
    set seatno = dropgaps((select m.seatNo_to from Movement m where m.seatNo_from = b.seatno));
    drop table if exists Movement;
end;
$$;

start transaction read write isolation level snapshot;
-- Рассмотрим проблемы.
-- Косых чтений не будет, так как мы собираем все в временную табличку, которая зависит от таблички билетов, затем изменяем табличку билетов в зависимости от временной. Все происходит поочередно, то есть косых записей не будет.
-- Однако пока мы уплотняли места, кто то мог успеть купить новое место, то есть нам нужен уровень как минимум снапшота