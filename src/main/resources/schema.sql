drop table if exists board cascade;
drop table if exists comment cascade;
drop table if exists app_user cascade;

create table board
(
    id                bigint generated by default as identity,
    created_at        timestamp(6) not null,
    last_modified_at  timestamp(6) not null,
    deleted_at        timestamp(6),
    user_id           bigint not null,
    title             varchar(40) not null,
    category          varchar(20) not null,
    tag               varchar(20) not null,
    content           varchar(2000) not null,
    count_heart       int not null,
    primary key (id)
);
create table comment
(
    id                bigint generated by default as identity,
    created_at        timestamp(6) not null,
    last_modified_at  timestamp(6) not null,
    deleted_at        timestamp(6),
    content           varchar(2000) not null,
    user_id           bigint not null,
    board_id          bigint not null,
    primary key (id)
);
create table app_user
(
    id                bigint generated by default as identity,
    created_at        timestamp(6) not null,
    last_modified_at  timestamp(6) not null,
    username          varchar(10) not null,
    password          varchar(511) not null,
    primary key (id)
);
create table heart
(
    id                bigint generated by default as identity,
    user_id           bigint not null,
    board_id          bigint not null,
    primary key (id)
);