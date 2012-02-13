# OCRE schema
 
# --- !Ups

CREATE SEQUENCE user_id_seq;
CREATE TABLE User (
    id integer NOT NULL DEFAULT nextval('user_id_seq'),
    email varchar(255), 
    password varchar(12)
);
 
# --- !Downs
 
DROP TABLE User;
DROP SEQUENCE user_id_seq;