drop database if exists packin_up;
create database packin_up;
use packin_up;

create table users (
    user_id int primary key auto_increment,
    username varchar(50),
    email varchar(100) unique,
    `password` text
);

create table trip_types (
    trip_type_id int primary key auto_increment,
    trip_type_name varchar(50) unique,
    trip_type_description text
    );

create table templates (
    template_id int primary key auto_increment,
    template_name varchar(50) unique,
    template_description text,
    template_trip_type_id int,
    foreign key (template_trip_type_id) references trip_types(trip_type_id),
    template_user_id int,
    foreign key (template_user_id) references users(user_id)
);