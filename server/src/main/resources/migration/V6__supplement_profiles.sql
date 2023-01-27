alter table profiles
    add address varchar(255);

alter table profiles
    add postal_code varchar(255);

alter table profiles
    add city varchar(255);

alter table profiles
    add country_id bigint;
alter table profiles
    add constraint profiles_country_id_fkey foreign key (country_id) references country (id);

alter table profiles
    add main_language_id bigint;
alter table profiles
    add constraint profiles_main_language_id_fkey foreign key (main_language_id) references main_language (id);

alter table profiles
    add other_languages varchar(255);

alter table profiles
    add birth_date date;

alter table profiles
    add place_of_birth_city varchar(255);

alter table profiles
    add place_of_birth_country varchar(255);

alter table profiles
    add citizenship_id bigint;
alter table profiles
    add constraint profiles_citizenship_id_fkey foreign key (citizenship_id) references country (id);

alter table profiles
    add origin_id bigint;
alter table profiles
    add constraint profiles_origin_id_fkey foreign key (origin_id) references origin (id);


