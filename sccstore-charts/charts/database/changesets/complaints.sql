--liquibase formatted sql
--changeset sccstore-changeset:1
create table complaints (
  compl_id SERIAL PRIMARY KEY, 
  complaint VARCHAR (255) NOT NULL
);
insert into complaints (complaint) values ('Just a first initial complaint');
insert into complaints (complaint) values ('And another one initially loaded');