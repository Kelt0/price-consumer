create database price_db;

create table price_track(
    track_id serial primary key,
    suplied_price int not null,
    track_time timestamp not null
);