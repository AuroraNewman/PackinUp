drop database if exists packin_up_test;
create database packin_up_test;
use packin_up_test;

create table users (
    user_id int primary key auto_increment,
    username varchar(50),
    email varchar(100) unique,
    password_hash text
);

delimiter //
create procedure set_known_good_state()
begin
    delete from users;
    alter table users auto_increment = 1;

    insert into users(user_id, username, email, password_hash) values
            (1, 'Bernie', 'Bernie@rubiber.com', 'verygoodpassword'),
            (2, 'Bianca', 'Bianca@rubiber.com', 'verygoodpassword'),
            (3, 'Rufus', 'rufus@rubiber.com', 'verygoodpassword');
end //
delimiter ;