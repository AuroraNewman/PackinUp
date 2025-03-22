drop database if exists packin_up_test;
create database packin_up_test;
use packin_up_test;

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
    foreign key (template_trip_type_id) references trip_types(trip_type_id) on delete cascade,
    template_user_id int,
    foreign key (template_user_id) references users(user_id) on delete cascade
);

create table categories (
    category_id int primary key auto_increment,
    category_name varchar(50),
    category_color text
);

create table items (
    item_id int primary key auto_increment,
    item_name varchar(50),
    item_user_id int,
    item_category_id int,
    foreign key (item_category_id) references categories(category_id) on delete cascade,
    foreign key (item_user_id) references users(user_id) on delete cascade
);

create table templates_items (
    template_item_id int primary key auto_increment,
    template_item_template_id int,
    foreign key (template_item_template_id) references templates(template_id) on delete cascade,
    template_item_item_id int,
    foreign key (template_item_item_id) references items(item_id) on delete cascade
);

delimiter //
create procedure set_known_good_state()
begin
    delete from categories;
    alter table categories auto_increment = 1;
    delete from templates_items;
    alter table templates_items auto_increment = 1;
    delete from items;
    alter table items auto_increment = 1;
	delete from templates;
    alter table templates auto_increment = 1;
    delete from trip_types;
    alter table trip_types auto_increment = 1;
    delete from users;
    alter table users auto_increment = 1;


    insert into users(user_id, username, email, `password`) values
            (1, 'Bernie', 'Bernie@rubiber.com', 'veryg00dPassword!'),
            (2, 'Bianca', 'Bianca@rubiber.com', 'veryg00dPassword!'),
            (3, 'Rufus', 'rufus@rubiber.com', 'veryg00dPassword!');

    insert into trip_types(trip_type_id, trip_type_name, trip_type_description) values
            (1, 'General', 'Not specified'),
            (2, 'Vacation', 'A trip for vacation purposes.'),
            (3, 'Family', 'A trip for family purposes.');

    insert into templates(template_id, template_name, template_description, template_trip_type_id, template_user_id) values
            (1, 'General', 'Not specified', 1, 1),
            (2, 'Vacation', 'A trip for vacation purposes.', 2, 2),
            (3, 'Family', 'A trip for family purposes.', 3, 3);

end //
delimiter ;