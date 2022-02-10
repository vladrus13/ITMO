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

start transaction read write isolation level repeatable read;
-- Рассмотрим проблемы.
-- Косой записи не будет, так как у нас одна табличка
-- Фантомная запись не является проблемой, так как мы узнаем, обновится ли нужный билет, и, если не удалось, мы можем повести себя соответственно
-- Неповторяющее чтение является проблемой, так как мы можем отобрать билет у человека, и затем кто то успеет забронировать его
-- Наш выбор - repeatable read