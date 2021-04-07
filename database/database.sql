create table if not exists users
(
    user_id   serial       not null
        constraint users_pk
            primary key,
    login     varchar(12)  not null,
    password  varchar(72)  not null,
    firstname varchar(20)  not null,
    lastname  varchar(20)  not null,
    email     varchar(320) not null,
    role      varchar(16)  not null
);

alter table users
    owner to postgres;

create table if not exists games
(
    game_id     serial       not null
        constraint game_pk
            primary key,
    title       varchar(35)  not null,
    image_path  varchar(300) not null,
    description varchar(300),
    price       numeric,
    trailer     varchar(150)
);

alter table games
    owner to postgres;

create table if not exists codes
(
    code_id   serial                not null
        constraint code_pk
            primary key,
    game_code varchar(29)           not null,
    sold      boolean default false not null,
    game_id   integer               not null
        constraint game_id_fk
            references games
);

alter table codes
    owner to postgres;

create unique index if not exists codes_game_code_uindex
    on codes (game_code);

create table if not exists genres
(
    genre_id serial      not null
        constraint genre_pk
            primary key,
    name     varchar(30) not null
);

alter table genres
    owner to postgres;

create unique index if not exists genre_genre_id_uindex
    on genres (genre_id);

create unique index if not exists genre_name_uindex
    on genres (name);

create table if not exists genre_game
(
    game_id  integer not null
        constraint game_id
            references games
            on update cascade on delete cascade,
    genre_id integer not null
        constraint genre_id
            references genres
            on update cascade on delete cascade
);

alter table genre_game
    owner to postgres;

create table if not exists categories
(
    category_id serial      not null
        constraint category_pk
            primary key,
    name        varchar(30) not null
);

alter table categories
    owner to postgres;

create unique index if not exists category_category_id_uindex
    on categories (category_id);

create unique index if not exists category_name_uindex
    on categories (name);

create table if not exists category_game
(
    game_id     integer not null
        constraint game_id_fk
            references games
            on update cascade on delete cascade,
    category_id integer not null
        constraint category_id_fk
            references categories
            on update cascade on delete cascade
);

alter table category_game
    owner to postgres;

create table if not exists coupons
(
    coupon_id serial             not null
        constraint coupons_pk
            primary key,
    code      varchar(10)        not null,
    discount  smallint default 0 not null,
    amount    integer  default 0 not null
);

alter table coupons
    owner to postgres;

create table if not exists orders
(
    order_id    serial            not null
        constraint orders_pk
            primary key,
    user_id     integer           not null
        constraint users_fk
            references users
            on update cascade on delete cascade,
    total_price numeric default 0 not null,
    coupon_id   integer
        constraint coupon_fk
            references coupons
            on update cascade on delete cascade
);

alter table orders
    owner to postgres;

create unique index if not exists orders_order_id_uindex
    on orders (order_id);

create unique index if not exists orders_order_id_uindex_2
    on orders (order_id);

create unique index if not exists coupons_code_uindex
    on coupons (code);

create unique index if not exists coupons_coupon_id_uindex
    on coupons (coupon_id);

create table if not exists game_order
(
    game_id       integer not null
        constraint game_fk
            references games
            on update cascade on delete cascade,
    order_id      integer not null
        constraint order_fk
            references orders
            on update cascade on delete cascade,
    game_order_id serial  not null
        constraint game_order_pk
            primary key
);

alter table game_order
    owner to postgres;

create unique index if not exists game_order_game_order_id_uindex
    on game_order (game_order_id);

create table if not exists password_tokens
(
    token_id      serial                                    not null
        constraint password_tokens_pk
            primary key,
    user_id       integer                                   not null
        constraint password_tokens_user
            references users
            on update cascade on delete cascade,
    reset_token   varchar(32) default md5((random())::text) not null,
    creation_date timestamp   default CURRENT_TIMESTAMP
);

alter table password_tokens
    owner to postgres;

create unique index if not exists password_tokens_token_id_uindex
    on password_tokens (token_id);

create unique index if not exists password_tokens_reset_token_uindex
    on password_tokens (reset_token);

create unique index if not exists password_tokens_user_id_uindex
    on password_tokens (user_id);

