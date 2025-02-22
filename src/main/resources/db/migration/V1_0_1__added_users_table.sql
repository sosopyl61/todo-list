create table public.users
(
    id          bigserial
        constraint users_pk
            primary key,
    firstname   varchar(100)               not null,
    second_name varchar(100)               not null,
    created     timestamp(6) default now() not null,
    changed     timestamp(6),
    age         integer
);