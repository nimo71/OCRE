# OCRE schema
 
# --- !Ups

CREATE SEQUENCE document_id_seq;
CREATE TABLE Document (
    documentId bigint(20) NOT NULL DEFAULT nextval('document_id_seq'),
    url varchar(1000),
    primary key (documentId)
);
 
# --- !Downs
 
DROP TABLE Document;
DROP SEQUENCE document_id_seq;