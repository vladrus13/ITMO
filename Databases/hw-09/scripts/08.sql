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

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '101B', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '1D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '2D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '3D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '4D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '5D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '6D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '7D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '8D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '9D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, null, 'sale', '10D', null);

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, 1, 'book', '51Z', flight_time(22, 11, 0));

select *
from bookingsandsales;

call CompressSeats(1);

select *
from bookingsandsales;