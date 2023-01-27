create table if not exists profile_fields
(
    id           bigserial    not null
        constraint profile_fields_pkey
            primary key,
    caption      varchar(255) not null,
    extra        text,
    field_type   varchar(255) not null,
    is_mandatory boolean      not null,
    sort_order   integer      not null,
    is_unique    boolean      not null,
    code         varchar(50)  not null,
    section_code varchar(50)  not null,
    section_id   bigint       not null

);
create table if not exists profile_field_sections
(
    id         bigserial    not null
        constraint profile_field_section_pkey
            primary key,
    caption    varchar(255) not null,
    sort_order integer      not null,
    code       varchar(50)  not null
);
create table if not exists profile_field_values
(
    id           bigserial not null
        constraint profile_field_values_pkey
            primary key,
    section_code text      not null,
    value        text,
    owner_id     bigint    not null,
    field_id     bigint    not null
);

alter table profile_fields
    add constraint fk_profile_fields_section_id_pkey
        foreign key (section_id)
            references profile_field_sections (id);
alter table profile_field_values
    add constraint fk_profile_fields_value_owner_id
        foreign key (owner_id)
            references profiles (id);
alter table profile_field_values
    add constraint fk_profile_fields_value_field_id
        foreign key (field_id)
            references profile_fields (id);

insert into profile_field_sections(caption, sort_order, code)
values ('Profile Details', 1, 'profile_details');

insert into profile_fields(caption, extra, field_type, is_mandatory, sort_order, is_unique, code, section_code,
                           section_id)
values ('First name', null, 'TEXT', true, 1, false, 'first_name', 'profile_details', 1),
       ('Last name', null, 'TEXT', true, 2, false, 'last_name', 'profile_details', 1),
       ('Email', null, 'TEXT', true, 3, false, 'email', 'profile_details', 1),
       ('Gender', '{"items":["Madam","Sir"]}', 'LIST', true, 4, false, 'gender', 'profile_details', 1),
       ('Phone Number', null, 'NUMERIC', true, 5, false, 'phone', 'profile_details', 1),
       ('Address : Street and n°', null, 'TEXT', false, 6, false, 'address', 'profile_details', 1),
       ('Postal Code', null, 'TEXT', false, 7, false, 'postal_code', 'profile_details', 1),
       ('City', null, 'TEXT', false, 8, false, 'city', 'profile_details', 1),
       ('Country', '{"key":"country"}', 'LOOKUP', true, 9, false, 'country', 'profile_details', 1),
       ('Main language', '{"key":"language"}', 'LOOKUP', true, 10, false, 'main_language', 'profile_details', 1),
       ('Other languages', null, 'TEXT', false, 11, false, 'other_language', 'profile_details', 1),
       ('Date of Birth', null, 'DATE', true, 12, false, 'dare_of_birth', 'profile_details', 1),
       ('Place of Birth (city)', null, 'TEXT', false, 13, false, 'place_of_birth_city', 'profile_details', 1),
       ('Place of Birth (country)', null, 'TEXT', true, 14, false, 'place_of_birth_country', 'profile_details', 1),
       ('Citizenship', '{"key":"country"}', 'LOOKUP', false, 15, false, 'citizenship', 'profile_details', 1),
       ('Origin',
        '{"items":["Friend/Family","presse/internet","Bank","Adem","Social partner","business partner","other"]}',
        'LIST',
        true, 16, false, 'origin', 'profile_details', 1);

