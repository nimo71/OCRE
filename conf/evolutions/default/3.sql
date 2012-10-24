# OCRE schema
 
# --- !Ups

CREATE TABLE UserDocument (
	userId bigint(20) NOT NULL,
    documentId bigint(20) NOT NULL,
    primary key (userId, documentId)
);
 
# --- !Downs
 
DROP TABLE UserDocument;