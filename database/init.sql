create table if not exists client (
	id int auto_increment primary key,
	name varchar(255) not null,
	email varchar(255) not null unique
);

create index idx_client_email on client(email);