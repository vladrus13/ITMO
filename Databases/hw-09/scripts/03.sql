create or replace function ExtendReservation(UserId integer, Pass varchar(255), FlightId integer, SeatNo varchar(4))
    returns boolean
    language plpgsql
as
$$
declare
    seatUserId bookingsandsales;
begin
    -- Проверяет, действителен ли этот поьзователь, есть ли такой рейс, и не улетел ли он
    if (not checkUserAndFlight(UserId, Pass, FlightId)) then
        return false;
    end if;
    -- Смотрим, кто же счастливый обладатель брони на это место
    seatUserId := getReservedPlaces(ExtendReservation.FlightId, ExtendReservation.SeatNo, true);
    -- Если оказалось, что обладателя места вовсе нет, или же он не является нами
    if (seatUserId is null or ExtendReservation.UserId != seatUserId.userid) then
        return false;
    end if;
    -- Обновляем бронь вперед РОВНО на 3 дня
    -- Примечание: было бы правильнее продлять бронь на 3 дня, но не дальше чем отправление самолета
    -- Однако задание было про ровно 3 дня. Да и эта бронь не будет мешать уже отправленному рейсу,
    -- он не сможет купить билет по этой брони, так как самолет уже улетел
    update bookingsandsales b
    set time = (seatUserId.time + '3 day'::interval)
    where b.flightid = ExtendReservation.FlightId
      and b.seatno = ExtendReservation.SeatNo;
    return true;
end;
$$;

select * from bookingsandsales;

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, 1, 'book', '2A', flight_time(22, 11, 0));

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, 1, 'book', '1D', flight_time(22, 11, 0));

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, 1, 'book', '5A', flight_time(22, 11, 0));

select ExtendReservation(1, 'password2', 1, '1A');

select ExtendReservation(1, 'password1', 1, '1A');

select ExtendReservation(1, 'password1', 1, '1D');

select ExtendReservation(1, 'password1', 1, '3A');

select ExtendReservation(1, 'password1', 11, '001A');

select * from bookingsandsales;