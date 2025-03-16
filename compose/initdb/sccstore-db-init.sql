create user sccstore with password 'sccstore';
create database sccstore;
GRANT ALL PRIVILEGES ON DATABASE sccstore to sccstore;
\connect sccstore
create table complaints (compl_id SERIAL PRIMARY KEY, complaint VARCHAR (255) NOT NULL);
insert into complaints (complaint) values ('INITIAL - Just a first initial complaint');
insert into complaints (complaint) values ('INITIAL - And another one initially loaded');
GRANT ALL PRIVILEGES ON table complaints to sccstore;