# OCRE schema
 
# --- !Ups

CREATE SEQUENCE document_id_seq;
CREATE TABLE Document (
    id bigint(20) NOT NULL DEFAULT nextval('document_id_seq'),
    url varchar(1000),
    primary key (id)
);
 
# --- !Downs
 
DROP TABLE Document;
DROP SEQUENCE document_id_seq;