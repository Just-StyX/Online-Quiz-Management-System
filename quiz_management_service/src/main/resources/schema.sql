create database quiz;

create table if not exists questions (
    id serial primary key,
    question text not null,
    answer text not null,
    options text,
    subject not null,
    points float not null,
    level varchar
);

create table if not exists users (
    id serial primary key,
    username varchar not null unique,
    password varchar not null,
    first_name varchar not null,
    middle_name varchar,
    last_name varchar not null,
    unique_id varchar unique not null,
    role char(5) not null
);