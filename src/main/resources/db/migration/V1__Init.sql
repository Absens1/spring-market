create table users_shipping_info (
    user_id                         int primary key,
    country                         varchar(50) not null,
    city                            varchar(80) not null,
    address                         varchar(100) not null,
    zip_code                        int not null,
    created_at                      timestamp default current_timestamp,
    updated_at                      timestamp default current_timestamp
);


create table users (
    id                      bigserial primary key,
    username                varchar(30) not null unique,
    password                varchar(80) not null,
    first_name              varchar(100),
    last_name               varchar(100),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

create table roles (
    id                      bigserial primary key,
    name                    varchar(50) not null unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

CREATE TABLE users_roles (
          user_id                 bigint not null references users (id),
          role_id                 bigint not null references roles (id),
          primary key (user_id, role_id)
);

insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into users (username, password, first_name, last_name)
values
('user', '$2a$10$JCCSVwG4yEvwDON/pnBYM.orOO1UmIPSaNlNZsAMO608cU079UTo.', 'John', 'Doe');

insert into users_shipping_info (user_id, country, city, address, zip_code)
values
(1, 'Spain', 'Coslada', 'Méndez Núñez', 29008);

insert into users (username, password)
values
('admin', '$2a$10$JCCSVwG4yEvwDON/pnBYM.orOO1UmIPSaNlNZsAMO608cU079UTo.');

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);

create table categories (
    id                              bigserial primary key,
    title                           varchar(255),
    created_at                      timestamp default current_timestamp,
    updated_at                      timestamp default current_timestamp
);

insert into categories (title)
values
  ('Category1'),
  ('Category2'),
  ('Category3'),
  ('Category4'),
  ('Category5'),
  ('Category6'),
  ('Category7'),
  ('Category8'),
  ('Category9'),
  ('Category10');

create table products (
    id                              bigserial primary key,
    title                           varchar(255),
    price                           numeric (8, 2),
    category_id                     bigint references categories (id),
    image                           varchar(255),
    created_at                      timestamp default current_timestamp,
    updated_at                      timestamp default current_timestamp
);

insert into products (title, price, category_id, image)
values
  ('Product1', 88, 1, 'assets/images/demo/product-1.png'),
  ('Product2', 64, 2, 'assets/images/demo/product-2.png'),
  ('Product3', 81, 1, 'assets/images/demo/product-3.png'),
  ('Product4', 98, 3, 'assets/images/demo/product-4.png'),
  ('Product5', 90, 1, 'assets/images/demo/product-5.png'),
  ('Product6', 77, 2, 'assets/images/demo/product-6.png'),
  ('Product7', 50, 2, 'assets/images/demo/product-7.png'),
  ('Product8', 56, 4, 'assets/images/demo/product-8.png'),
  ('Product9', 72, 2, 'assets/images/demo/product-9.png'),
  ('Product10', 39, 1, 'assets/images/demo/product-10.png'),
  ('Product11', 15, 1, 'assets/images/demo/product-11.png'),
  ('Product12', 87, 3, 'assets/images/demo/product-12.png'),
  ('Product13', 40, 1, 'assets/images/demo/product-1.png'),
  ('Product14', 40, 2, 'assets/images/demo/product-2.png'),
  ('Product15', 25, 2, 'assets/images/demo/product-3.png'),
  ('Product16', 108, 4, 'assets/images/demo/product-4.png'),
  ('Product17', 500, 1, 'assets/images/demo/product-5.png'),
  ('Product18', 69, 4, 'assets/images/demo/product-6.png'),
  ('Product19', 99, 1, 'assets/images/demo/product-7.png'),
  ('Product20', 31, 3, 'assets/images/demo/product-8.png'),
  ('Product21', 200, 4, 'assets/images/demo/product-9.png'),
  ('Product22', 130, 1, 'assets/images/demo/product-10.png'),
  ('Product23', 27, 1, 'assets/images/demo/product-11.png'),
  ('Product24', 19, 3, 'assets/images/demo/product-12.png'),
  ('Product25', 22, 1, 'assets/images/demo/product-1.png'),
  ('Product26', 600, 2, 'assets/images/demo/product-2.png'),
  ('Product27', 400, 2, 'assets/images/demo/product-3.png'),
  ('Product28', 204, 4, 'assets/images/demo/product-4.png'),
  ('Product29', 700, 1, 'assets/images/demo/product-5.png'),
  ('Product30', 100, 4, 'assets/images/demo/product-6.png'),
  ('Product31', 40, 1, 'assets/images/demo/product-7.png'),
  ('Product32', 22, 3, 'assets/images/demo/product-8.png'),
  ('Product33', 19, 4, 'assets/images/demo/product-9.png');

create table delivery_types (
    id                              bigserial primary key,
    title                           varchar(20),
    price                           numeric (8, 2),
    created_at                      timestamp default current_timestamp,
    updated_at                      timestamp default current_timestamp
);

insert into delivery_types (title, price)
values
  ('One-day Shipping', 199.00),
  ('Economy Shipping', 99.00),
  ('Free shipping', 0.00);

create table orders_shipping_info (
    id                              bigserial primary key,
    first_name                      varchar(100) not null,
    last_name                       varchar(100) not null,
    country                         varchar(50) not null,
    city                            varchar(80) not null,
    address                         varchar(100) not null,
    zip_code                        int not null,
    created_at                      timestamp default current_timestamp,
    updated_at                      timestamp default current_timestamp
);

insert into orders_shipping_info (first_name, last_name, country, city, address, zip_code)
values
  ('TestFirstName', 'TestLastName', 'TestCountry', 'TestCity', 'TestAddress', 123);

create table orders (
    id                              bigserial primary key,
    user_id                         bigint references users (id),
    delivery_type_id                bigint references delivery_types (id),
    orders_shipping_info_id         bigint references orders_shipping_info (id),
    price                           numeric (8, 2),
    card_number                     varchar(20),
    created_at                      timestamp default current_timestamp,
    updated_at                      timestamp default current_timestamp
);

insert into orders (user_id, delivery_type_id, orders_shipping_info_id, price, card_number)
values
  (1, 3, 1, 1361.00, '00000000000');


create table order_items (
    id                              bigserial primary key,
    order_id                        bigint references orders (id),
    product_id                      bigint references products (id),
    quantity                        int,
    price_per_product               numeric (8, 2),
    price                           numeric (8, 2),
    created_at                      timestamp default current_timestamp,
    updated_at                      timestamp default current_timestamp
);

insert into order_items (order_id, product_id, quantity, price_per_product, price)
values
  (1, 1, 5, 88, 440),
  (1, 2, 3, 64, 192),
  (1, 3, 9, 81, 729);