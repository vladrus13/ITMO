create or replace function FlightStat(UserId integer, Pass varchar(255), FlightId integer)
    returns table
            (
                canBook    boolean,
                canBuy     boolean,
                freeSeat   integer,
                bookSeat   integer,
                sailedSeat integer
            )
    language plpgsql
as
$$
declare
    P_all         int;
    P_salesBooked int;
    P_bookedUser  int;
    P_free        int;
    P_book        int;
    P_sailed      int;
    P_canBook     boolean;
    P_canBuy      boolean;
begin
    -- Следует пояснить, почему же я не использовал view по активным забронированным,
    -- хотя его использование тут прямо таки напрашивается

    -- У нас всегда берутся всегда не только активные забронированные, но и еще
    -- с каким то условием. Значит, что view будет давать подзапрос. Поэтому я решил
    -- что лучше чуть чуть копипасты, чем подзапрос
    P_all := (select count(*)
              from seats
              where planeid = (select f.planeid from flights f where f.flightid = FlightStat.FlightId));
    P_book := (select count(*)
               from bookingsandsales b
               where b.time > now()
                 and b.CastType = 'book'
                 and b.flightid = FlightStat.FlightId);
    P_sailed :=
            (select count(*) from bookingsandsales b where b.casttype = 'sale' and b.flightid = FlightStat.FlightId);
    P_salesBooked := P_book + P_sailed;
    P_free := P_all - P_salesBooked;
    -- Если мы не авторизованны или данный рейс улетел, то любое связанное с пользователями или рейсом действие считает неопределенным
    if (not checkUserAndFlight(UserId, Pass, FlightId)) then
        P_bookedUser := null;
        P_canBook = null;
        P_canBuy = null;
    else
        P_bookedUser := (select count(*)
                         from bookingsandsales b
                         where FlightStat.UserId = b.userid
                           and b.flightid = FlightStat.FlightId
                           and b.time > now()
                           and b.casttype = 'book');
        P_canBook := P_free > 0;
        P_canBuy := (P_free > 0) or (P_bookedUser > 0);
    end if;
    return query (select *
                  from (values (P_canBook::boolean, P_canBuy::bool, P_free, P_book, P_sailed)) as t(canBook, canBuy, free, book, sailed));
end;
$$;

start transaction read only isolation level snapshot;
-- Рассмотрим проблемы.
-- Косая запись не является проблемой, так как у нас нет записей.
-- Однако есть расхождение в фантомном чтении и неповторяющемся чтении. Мы можем не обращать внимание на небольшие погрешности, и заявить read uncommitted, так как это все равно статистика
-- Но если нам важно, что бы данные были максимально точные для какого то момента времени, то следует поставить snapshot, что бы избежать случаев, когда у юзер забронировал место в незабронированных местах (то есть общее количество мест было бы 0, мы перешли к валидацции юзера, и тут он забронировал место, и мы бы вевели что он забронировал 1 место, что весьма противоречиво выглдит
-- Наш выбор - repeatable read (или read uncommitted, read committed в зависимости от важности статистики)