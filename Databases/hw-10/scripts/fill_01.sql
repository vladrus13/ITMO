create or replace function flight_time(date integer, hour integer, minutes integer)
    returns timestamp
    language plpgsql as
$$
begin
    return cast('December ' || date ||
                ', 2021 ' || hour ||
                ':' || minutes ||
                ':00' as timestamp);
end;
$$;

insert into Flights (FlightId, FlightTime, PlaneId)
values (1, flight_time(22, 10, 0), 0),
       (2, flight_time(23, 10, 0), 1),
       (3, flight_time(23, 10, 0), 2),
       (4, flight_time(24, 10, 0), 3),
       (5, flight_time(24, 10, 0), 0),
       (6, flight_time(24, 10, 0), 1),
       (7, flight_time(26, 10, 0), 2),
       (8, flight_time(26, 10, 0), 3),
       (9, flight_time(26, 10, 0), 3);

do
$$
    declare
        it record;
    begin
        for it in 0..4
            loop
                for width in 0..5
                    loop
                        for length in 0..9
                            loop
                                insert into seats(PlaneId, SeatNo)
                                values (it, cast(length as varchar(3)) || (chr(ascii('A') + width)));
                            end loop;
                    end loop;
            end loop;
    end;
$$;
end;

insert into users(userid, passwordhash, passwordsalt)
VALUES (1, '9802530931535eea75c5bc5927e669f5', 'SALTABRACADABRA');

insert into bookingsandsales (flightid, userid, casttype, seatno, time)
values (1, 1, 'book', '1A', flight_time(22, 11, 0));