# OCRE schema
 
# --- !Ups

CREATE SEQUENCE user_id_seq;
CREATE TABLE User (
    id bigint(20) NOT NULL DEFAULT nextval('user_id_seq'),
    email varchar(255), 
    password varchar(40),
    primary key (id)
);
 
# --- !Downs
 
DROP TABLE User;
DROP SEQUENCE user_id_seq;