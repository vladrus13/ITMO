drop table if exists Seats cascade;

-- Здесь хранятся места в самолете
create table Seats
(
    PlaneId integer    not null,
    SeatNo  varchar(4) not null,

    constraint seats_pk primary key (PlaneId, SeatNo)
);

drop table if exists Flights cascade;

-- Здесь хранятся полеты. То есть одно отправление самолета -- одна запись.
create table Flights
(
    FlightId   integer   not null,
    FlightTime timestamp not null,
    PlaneId    integer   not null,

    constraint flights_pk primary key (FlightId)
);

drop table if exists Users cascade;

-- Здесь хранятся юзеры. Для каждого известен его id его пароль, и соль от пароля
create table Users
(
    UserId       integer   not null,
    PasswordHash char(256) not null,
    PasswordSalt char(256) not null,

    constraint users_pk primary key (UserId)
);

-- Состояние билета. Он может быть забронирован и может быть продан
drop type if exists buyingType cascade;
create type buyingType as enum ('book', 'sale');

drop table if exists BookingsAndSales cascade;

-- Табличка хранящая все приобретения людей. Это может быть бронь или покупка билета
create table BookingsAndSales
(
    FlightId integer    not null,
    SeatNo   varchar(4) not null,
    CastType buyingType not null,
    UserId   integer,
    Time     timestamp,

    constraint id_no_p primary key (FlightId, SeatNo),
    constraint f_id_f foreign key (FlightId) references Flights (FlightId) on delete cascade,
    constraint book_check check (CastType = 'sale' or (UserId is not null and Time is not null))
    -- Не можем поддержать обращение по foreign key к users, так как userId может быть null
    -- TODO const on userId
    -- TODO const on seat
);

-- Представление для всех неистекших бронирований.
-- К сожалению, редко было использовано, так как редко когда нам нужны все брони, без дополнительных условий
create or replace view RealBooks as
select flightid, userid, seatno
from bookingsandsales b
where b.time > now()
  and b.CastType = 'book';
