create table if not exists origin
(
    id   bigserial primary key,
    name varchar(255) not null
);
insert into origin(name)
values ('Friend/Family'),
       ('presse/internet'),
       ('Bank'),
       ('Adem'),
       ('Social partner'),
       ('business partner'),
       ('other');
