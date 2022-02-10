create or replace function FlightsStatistics(UserId integer, Pass varchar(255))
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
    -- В целом тут все понятно

    -- Следует пояснить, почему же я не использовал view по активным забронированным,
    -- хотя его использование тут прямо таки напрашивается

    -- У нас всегда берутся всегда не только активные забронированные, но и еще
    -- с каким то условием. Значит, что view будет давать подзапрос. Поэтому я решил
    -- что лучше чуть чуть копипасты, чем подзапрос
    P_all := (select count(*)
              from flights
                       natural join seats);
    P_book := (select count(*) from realbooks);
    P_sailed := (select count(*) from bookingsandsales b where b.casttype = 'sale');
    P_salesBooked := P_book + P_sailed;
    P_free := P_all - P_salesBooked;
    -- Если мы не авторизованны, то любое связанное с пользователями действие считает неопределенным
    if (not checkUser(UserId, Pass)) then
        P_bookedUser := null;
        P_canBook = null;
        P_canBuy = null;
    else
        P_bookedUser := (select count(*)
                         from bookingsandsales b
                         where FlightsStatistics.UserId = b.userid
                           and b.time > now()
                           and b.casttype = 'book');
        P_canBook := P_free > 0;
        P_canBuy := (P_free > 0) or (P_bookedUser > 0);
    end if;
    return query (select *
                  from (values (P_canBook, P_canBuy, P_free, P_book, P_sailed)) as t(canBook, canBuy, free, book, sailed));
end;
$$;

start transaction read only isolation level snapshot;
-- Рассмотрим проблемы.
-- Косая запись не является проблемой, так как у нас нет записей.
-- Однако есть расхождение в фантомном чтении и неповторяющемся чтении. Мы можем не обращать внимание на небольшие погрешности, и заявить read uncommitted, так как это все равно статистика
-- Но если нам важно, что бы данные были максимально точные для какого то момента времени, то следует поставить snapshot, что бы избежать случаев, когда у юзер забронировал место в незабронированных местах (то есть общее количество мест было бы 0, мы перешли к валидацции юзера, и тут он забронировал место, и мы бы вевели что он забронировал 1 место, что весьма противоречиво выглдит
-- Наш выбор - repeatable read (или read uncommitted, read committed в зависимости от важности статистики)