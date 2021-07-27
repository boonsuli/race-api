drop database if exists trailerplan;
create database trailerplan;

GRANT ALL privileges
   ON `trailerplan`.*
   TO 'mariadm'@'%';
CREATE USER 'mariadm'@'localhost'
   IDENTIFIED BY 'P@55w*rD';

-- ===================================
-- create production table
-- ===================================
USE trailerplan;
drop table if exists RaceEntity;

create table RaceEntity (
        id                  int not null auto_increment
    ,   race_name           VARCHAR(100) not null
    ,   challenge_name      VARCHAR(200) null
    ,   distance            FLOAT not null
    ,   race_date           varchar(10) null
    ,   elevation_up        int null
    ,   elevation_down      int null
    ,   itra_point          int null
    ,   departure_city      varchar(100) null
    ,   arrival_city        varchar(100) null
    ,   maximum_time        FLOAT null
    ,   country             VARCHAR(100) not null
    ,   city_region         varchar(100) null
    ,   url                 VARCHAR(200) not null
    ,   primary key (id)
);
