create table if not exists email_messages
(
    id       bigserial not null
        constraint email_message_pkey
            primary key,
    content  varchar(255),
    emails   varchar(255),
    event_id bigint,
    is_sent  boolean,
    send_at  timestamp,
    subject  varchar(255)
);

create table if not exists email_templates
(
    id      bigserial    not null
        constraint email_template_pkey
            primary key,
    content varchar(255) not null,
    name    varchar(255) not null,
    title   varchar(255) not null,
    type    varchar(255) not null
);

create table if not exists notification_historys
(
    id                bigserial    not null
        constraint notification_history_pkey
            primary key,
    content           varchar(255) not null,
    created_by        varchar(255) not null,
    created_date      timestamp    not null,
    notification_type varchar(255) not null,
    recipients        varchar(255) not null,
    title             varchar(255) not null
);

create table if not exists profiles
(
    id           bigserial    not null
        constraint profile_pkey
            primary key,
    email        varchar(255),
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    created_date timestamp    not null,
    gender       varchar(255) not null,
    mobile_phone varchar(255)
);

create table if not exists roles
(
    id   bigserial    not null
        constraint role_pkey
            primary key,
    code varchar(255) not null
        constraint uk_c36say97xydpmgigg38qv5l2p
            unique,
    name varchar(255) not null
);

create table if not exists roles_permissions
(
    role_id    bigint       not null
        constraint fka6jx8n8xkesmjmv6jqug6bg68
            references roles,
    permission varchar(255) not null
);

create table if not exists users
(
    id                          bigserial    not null
        constraint user_pkey
            primary key,
    email                       varchar(255),
    first_name                  varchar(255) not null,
    last_name                   varchar(255) not null,
    address                     varchar(255),
    already_volunteered         varchar(255),
    availability                varchar(255),
    citizenship                 varchar(255),
    current_occupation          varchar(255),
    enabled                     boolean      not null,
    mobile_phone                varchar(255),
    password                    varchar(255) not null,
    phone_number                varchar(255),
    photo_content_type          varchar(255),
    photo_name                  varchar(255),
    photo_path                  varchar(255),
    postal_code                 varchar(255),
    preferred_working_languages varchar(255),
    specialization              varchar(255),
    spoken_languages            varchar(255),
    street                      varchar(255),
    support                     varchar(255),
    support_other               varchar(255),
    support_promoters           varchar(255),
    support_promoters_other     varchar(255),
    username                    varchar(255) not null
        constraint uk_sb8bbouer5wak8vyiiy4pf2bx
            unique,
    role_id                     bigint       not null
        constraint fkdl9dqp078pc03g6kdnxmnlqpc
            references roles
);

create table if not exists events
(
    id              bigserial    not null
        constraint event_pkey
            primary key,
    all_day         boolean      not null,
    content         varchar(255) not null,
    created_date    timestamp    not null,
    end_date        timestamp    not null,
    notify_at       timestamp    not null,
    start_date      timestamp    not null,
    title           varchar(255) not null,
    created_user_id bigint
        constraint fk24fr72dr7no1rwir9tjqim119
            references users
);

create table if not exists reset_user_password_token
(
    id          bigserial not null
        constraint reset_user_password_token_pkey
            primary key,
    create_date timestamp not null,
    expire_date timestamp not null,
    token       varchar(255),
    user_id     bigint    not null
        constraint fkqbjx0sclqa6jsaxj9wd3s9kjx
            references users
);

create table if not exists user_profile_views
(
    id         bigserial    not null
        constraint user_profile_view_pkey
            primary key,
    email      varchar(255),
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    entity_id  bigint       not null,
    full_name  varchar(255) not null,
    is_user    boolean      not null
);

create table if not exists workflows
(
    id          bigserial    not null
        constraint workflow_pkey
            primary key,
    description varchar(255),
    is_default  boolean      not null,
    name        varchar(255) not null
        constraint uk_3je18ux0wru0pxv6un40yhbn4
            unique
);

create table if not exists states
(
    id          bigserial    not null
        constraint state_pkey
            primary key,
    description varchar(255),
    name        varchar(255) not null,
    state_type  varchar(255) not null,
    workflow_id bigint       not null
        constraint fkkmedakceh71phmho44c0b8jbo
            references workflows
);

create table if not exists actions
(
    id                  bigserial    not null
        constraint action_pkey
            primary key,
    description         varchar(255),
    name                varchar(255) not null,
    owner_state_id      bigint       not null
        constraint fknysjd1sbui85aqwoo2gh6iwmi
            references states,
    transition_state_id bigint       not null
        constraint fk9bvfg1xx4vcknquduvcapvrr7
            references states
);

create table if not exists activitys
(
    id            bigserial    not null
        constraint activity_pkey
            primary key,
    activity_type varchar(255) not null,
    content       varchar(255),
    description   varchar(255),
    name          varchar(255) not null,
    action_id     bigint       not null
        constraint fkclg9lalq17377u29efrg9faap
            references actions
);

create table if not exists applications
(
    id                  bigserial    not null
        constraint application_pkey
            primary key,
    comment             varchar(255),
    completed           boolean      not null,
    created_date        timestamp    not null,
    is_deleted          boolean      not null,
    name                varchar(255) not null,
    status_changed_at   timestamp    not null,
    created_user_id     bigint
        constraint fke17mdpab4vvh0imf2diyt8668
            references users,
    current_state_id    bigint
        constraint fk1kg7elubltvxrng5wvln9ppe5
            references states,
    profile_id          bigint       not null
        constraint fk2bteh044rjpejdb58vaq6rrr3
            references profiles,
    responsible_user_id bigint       not null
        constraint fkkhi8yi5che7fo57c3w0e7klok
            references users,
    workflow_id         bigint       not null
        constraint fk6wb0b8jxn76290ohkqy0q0qvw
            references workflows
);

create table if not exists application_attachments
(
    id              bigserial    not null
        constraint application_attachment_pkey
            primary key,
    content_type    varchar(255) not null,
    created_date    timestamp    not null,
    name            varchar(255) not null,
    path            varchar(255) not null,
    application_id  bigint
        constraint fkea4hyfb52a714pv6w7vv38rjp
            references applications,
    created_user_id bigint
        constraint fk1j96iyd3ptjjr826ubqgheiny
            references users
);

create table if not exists application_state_permissions
(
    type           varchar(31) not null,
    id             bigserial   not null
        constraint application_state_permission_pkey
            primary key,
    application_id bigint      not null
        constraint fk9rq06d0pykave73ad25389cls
            references applications,
    state_id       bigint      not null
        constraint fk5j45btn35nar9as2ehkj1srmq
            references states,
    reference_id   bigint      not null
        constraint fkeoaoh48b0639eyr1jeu3v3d74
            references roles
);

create table if not exists application_logs
(
    id              bigserial not null
        constraint application_log_pkey
            primary key,
    created_date    timestamp not null,
    note            varchar(255),
    application_id  bigint    not null
        constraint fknmq7q34bgutsjx9vwxhmupu7s
            references applications,
    created_user_id bigint    not null
        constraint fk18c0j7m7qkfhvkn8r980x5c25
            references users,
    from_state_id   bigint
        constraint fklnf2p9m6e4p9s2a8nnayhcoqh
            references states,
    to_state_id     bigint    not null
        constraint fk6egk1dhpqrowfv8jsbjwo4l3
            references states
);

create table if not exists event_participants
(
    type           varchar(31) not null,
    id             bigserial   not null
        constraint event_participants_pkey
            primary key,
    event_id       bigint      not null
        constraint fk5232w1ta0icpkemgsxyw8a976
            references events,
    application_id bigint
        constraint fk5qai9ap75w931rfx8n5mvdcom
            references applications,
    reference_id   bigint      not null
        constraint fkkkygs9lst3mbf2v80x8jln84p
            references profiles
);

create table if not exists notifications
(
    id                bigserial    not null
        constraint notification_pkey
            primary key,
    created_date      timestamp    not null,
    done              boolean      not null,
    message           varchar(255) not null,
    notification_date timestamp    not null,
    application_id    bigint
        constraint fkjpfudgmrueigmyi86f5od2y71
            references applications,
    created_user_id   bigint
        constraint fkcjcwrsknae8bihtelkn355km
            references users
);

create table if not exists work_logs
(
    id              bigserial        not null
        constraint work_log_pkey
            primary key,
    created_date    timestamp        not null,
    entered_date    timestamp        not null,
    note            varchar(255)     not null,
    spent_hours     double precision not null,
    application_id  bigint
        constraint fkkdmy89by2kgqt7u4v3buucvpm
            references applications,
    created_user_id bigint
        constraint fkcl7oruqrcqktceh91k9uqn0xm
            references users
);

create table if not exists workflow_field_sections
(
    id          bigserial    not null
        constraint workflow_field_section_pkey
            primary key,
    caption     varchar(255) not null,
    sort_order  integer      not null,
    workflow_id bigint       not null
        constraint fkfhl1rayg6icjysxsl3yicrcht
            references workflows
);

create table if not exists workflow_fields
(
    id           bigserial    not null
        constraint workflow_field_pkey
            primary key,
    caption      varchar(255) not null,
    extra        varchar(255),
    field_type   varchar(255) not null,
    is_mandatory boolean      not null,
    sort_order   integer      not null,
    is_unique    boolean      not null,
    section_id   bigint       not null
        constraint fk98ooqt880re5j1ne5m20ftuyd
            references workflow_field_sections
);

create table if not exists application_field_values
(
    id                bigserial not null
        constraint application_field_value_pkey
            primary key,
    value             varchar(255),
    application_id    bigint    not null
        constraint fkocdhsqek6a8ggkdspkjpln19h
            references applications,
    workflow_field_id bigint    not null
        constraint fkn7pft3qkhksqoo3rrt16xdndb
            references workflow_fields
);

create table if not exists state_fields
(
    id           bigserial not null
        constraint state_field_pkey
            primary key,
    is_read_only boolean   not null,
    field_id     bigint    not null
        constraint fkpwit1dourklq98hqepgg59456
            references workflow_fields,
    state_id     bigint    not null
        constraint fk1g27kimr80hjblr4u8y26ibv8
            references states
);

create table if not exists workflow_state_permissions
(
    id           bigserial   not null
        constraint workflow_state_permission_pkey
            primary key,
    type         varchar(31) not null,
    state_id     bigint      not null
        constraint fkq0icbscbar6u1s1j1342rxvs7
            references states,
    reference_id bigint      not null
        constraint fkcwpw2dyikbp8are05qtfsskh4
            references roles
);

