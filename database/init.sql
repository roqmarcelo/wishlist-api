create table if not exists customer (
  id int auto_increment primary key,
  name varchar(255) not null,
  email varchar(255) not null unique
);

create index idx_customer_email on customer(email);

create table if not exists product (
  id int auto_increment primary key,
  customerId int not null,
  productId varchar(36) not null,
  title varchar(255) not null,
  image varchar(255) not null,
  price decimal(10, 2) not null,
  reviewScore float,
  foreign key (customerId) references customer(id),
  unique key id_customer_product (customerid, productId)
);

create index idx_customer_products on product(customerId);
create index idx_customer_product on product(customerId, productId);

create table if not exists loginuser (
    id int auto_increment primary key,
    username varchar(255) not null unique,
    password varchar(255) not null
);

create index idx_user on loginuser(username, password);

insert into loginuser (username, password) values ('admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=');