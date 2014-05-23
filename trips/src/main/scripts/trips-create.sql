create sequence hibernate_sequence;


create table users (
		id		integer primary key,
		user_type varchar(31) not null,
		enabled boolean not null,
		email varchar(255) not null,
		password varchar(255) not null
		
    );

  insert into users values(1,'ROLE_ADMIN','t','admin@gmail.com','abcd');
  insert into users values(2,'ROLE_USER','t','john@gmail.com','abcd');