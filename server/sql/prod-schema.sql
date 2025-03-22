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

create table template_items (
    template_item_id int primary key auto_increment,
    template_item_quantity int,
    template_item_is_checked boolean,
    template_item_template_id int,
    foreign key (template_item_template_id) references templates(template_id) on delete cascade,
    template_item_item_id int,
    foreign key (template_item_item_id) references items(item_id) on delete cascade
);

insert into users(user_id, username, email, `password`) values
            (1, 'Bernie', 'Bernie@rubiber.com', 'veryg00dPassword!'),
            (2, 'Bianca', 'Bianca@rubiber.com', 'veryg00dPassword!'),
            (3, 'Rufus', 'rufus@rubiber.com', 'veryg00dPassword!');


insert into trip_types(trip_type_id, trip_type_name, trip_type_description) values
            (1, 'General', 'Not specified'),
            (2, 'Vacation', 'A trip for vacation purposes.'),
            (3, 'Business Trip', 'It''s business time.');
insert into templates(template_id, template_name, template_description, template_trip_type_id, template_user_id) values
            (1, 'General', 'Not specified', 1, 1);

insert into categories(category_id, category_name, category_color) values
	(1, 'Weather', 'Blue');
    
insert into items(item_id, item_name, item_user_id, item_category_id) values
	(1, 'Gloves', 1, 1);