create table if not exists customer (
	id int auto_increment primary key,
	name varchar(255) not null,
	email varchar(255) not null unique
);

create index idx_customer_email on customer(email);