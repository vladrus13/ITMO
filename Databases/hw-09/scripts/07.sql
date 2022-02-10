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

select Reserve(1, 'password1', 1, '2A');

select Reserve(1, 'password1', 1, '3A');

select Reserve(1, 'password1', 1, '4A');

select Reserve(1, 'password1', 1, '5A');

select Reserve(1, 'password1', 1, '6A');

select BuyReserved(1, 'password1', 1, '1A');

select BuyReserved(1, 'password1', 1, '2A');

select *
from FlightStat(1, 'password1', 1);

select *
from FlightStat(1, 'password1', 2);