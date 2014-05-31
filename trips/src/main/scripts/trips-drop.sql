drop sequence hibernate_sequence;

    alter table Route drop constraint fk_from_location;

    alter table Route drop constraint fk_to_location;

    alter table Route drop constraint fk_trip;

    alter table trip_locations drop constraint fk_trip_location_location;

    alter table trip_locations drop constraint fk_trip_location_trip;

    alter table Trip drop constraint fk_trip_user;

drop table users;

drop table trip;

drop table route;

drop table location;
drop table trip_locations;