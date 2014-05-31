create sequence hibernate_sequence minvalue 100;

    create table Location (
        id int4 primary key,
        address varchar(255) not null,
        lat float8 not null,
        lon float8 not null,
        name varchar(32) not null,
        notes varchar(64) not null,
        location_type varchar(8) not null
    );

    create table Route (
        id int4 primary key,
        trip_id int4,
        travelMethod varchar(8),
        fromLocation_id int4,
        toLocation_id int4
    );

    create table Trip (
        id int4 primary key,
        user_id int4,
        defaultTravelMethod varchar(10),
        name varchar(255) not null,
        startTime date not null
    );

    insert into location values(1, 'here1', 1.3123,-31.132, 'no name1', '', '');
    insert into location values(2, 'here2', 1.3123,-31.132, 'no name2', '', '');
    insert into location values(3, 'here3', 1.3123,-31.132, 'no name3', '', '');
    insert into location values(4, 'here4', 1.3123,-31.132, 'no name4', '', '');
    insert into location values(5, 'here5', 1.3123,-31.132, 'no name5', '', '');

    insert into trip values(1, 1, 'DRIVING', 'MY TRIP', '2014-05-30 00:00:00');
    insert into trip values(2, 1, 'TRANSIT', 'SCHOOL TRIP', '2014-05-29 09:00:00');
    
    insert into route values(1, 1, 'DRIVING', 1,2);
    insert into route values(2, 1, 'DRIVING', 2,3);

    insert into route values(3, 2, 'DRIVING', 4,5);

    create table trip_locations (
        trip_id int4 not null,
        location_id int4 not null,
        step int4 not null,
        primary key (trip_id, step)
    );
    
    insert into trip_locations values(1, 1, 1);
    insert into trip_locations values(1, 2, 2);
    insert into trip_locations values(1, 3, 3);
    insert into trip_locations values(2, 1, 4);
    insert into trip_locations values(2, 2, 5);

    create table users (
        id int4 primary key,
        user_type varchar(31) not null,
        enabled boolean not null,
        email varchar(255),
        password varchar(255),
        client_id varchar(255) not null,
        client_secret varchar(255) not null,
        device_id varchar(255) not null
    );

    insert into users values(1,'ROLE_ADMIN','t','admin@gmail.com','abcd', '12345', 'abcde','98765');
    insert into users values(2,'ROLE_USER','t','john@gmail.com','abcd','24680','zyxwv','13579');
    insert into users values(3,'ROLE_USER','t','max@gmail.com','abcd','24680','zyxwv','13579');
    insert into users values(4,'ROLE_USER','t','josh@gmail.com','abcd','24680','zyxwv','13579');
    insert into users values(5,'ROLE_USER','t','amy@gmail.com','abcd','24680','zyxwv','13579');


    alter table Route add constraint fk_from_location 
        foreign key (fromLocation_id) references Location;

    alter table Route add constraint fk_to_location 
        foreign key (toLocation_id) references Location;

    alter table Route add constraint fk_trip 
        foreign key (trip_id) references Trip;

    alter table trip_locations add constraint fk_trip_location_location 
        foreign key (location_id) references Location;

    alter table trip_locations add constraint fk_trip_location_trip 
        foreign key (trip_id) references Trip;

    alter table Trip add constraint fk_trip_user 
        foreign key (user_id) references users;
