create table if not exists main_language
(
    id       bigserial primary key,
    name varchar(255) not null

);
insert into main_language(name)
values ('English'),
       ('French'),
       ('German'),
       ('Luxembourgish'),
       ('Other');

