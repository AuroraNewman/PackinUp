drop database if exists packin_up;
create database packin_up;
use packin_up;

create table users (
    user_id int primary key auto_increment,
    username varchar(50),
    email varchar(100) unique,
    `password` text
);