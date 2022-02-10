create or replace function Reserve(UserId integer, Pass varchar(255), FlightId integer, SeatNo varchar(4))
    returns boolean
    language plpgsql
as
$$
begin
    -- Проверяет, действительный ли пользователь, и есть ли такой рейс, и не улетел ли он
    if (not checkUserAndFlight(UserId, Pass, FlightId)) then
        return false;
    end if;
    -- Проверяем, существует ли такое место, и не занято ли оно
    if (not getFreePlace(FlightId, SeatNo, true)) then
        return false;
    end if;
    -- Вставляем нужную строку
    insert into bookingsandsales(flightid, seatno, userid, casttype, time)
    values (Reserve.FlightId, Reserve.SeatNo, Reserve.UserId, 'book', now() + '3 day'::interval);
    return true;
end;
$$;

start transaction read write isolation level read committed;
-- Рассмотрим проблемы
-- Косая запись не является проблемой, так как у нас одна табличка
-- Фантомная запись не является проблемой, так как мы узнаем, если insert не выполнится. То есть если два человека одновременно захотят запись, то один из них сделает ее, а другой поймет, что ее не сделал
-- Неповторяющее чтение не является проблемой, так как если у нас изменился пользователь или рейс, то мы скажем, что наша функция выполнилась раньше
-- Однако грязное чтение есть проблема, так как может быть добавлен рейс или пользователь или место, но потом просто отменен, а мы уже решим, что такое рейс существует, например
-- Наш выбор - read committed