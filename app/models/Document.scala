package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Document(id: Long, url: String)

object Document {
	
	val document = {
		get[Long]("documentId") ~ 
  		get[String]("url") map {
    		case documentId~url => Document(documentId, url)
  		}
	}
	
	/**
		Add a new document to the database
	 */	
	def create(url: String) = DB.withConnection { implicit c =>
   		SQL("insert into Document (url) values ({url})")
   			.on(('url -> url))
   			.executeUpdate
	}
	
	/**
		Retrieve a document by its url.
	 */
	def findByUrl(url: String): Option[Document] = DB.withConnection { implicit c => 
		SQL("select * from Document where url = {url}")
			.on('url -> url)
			.as(document *)
			.headOption
	}
	
	/**
		Retrieve all the documents in the user profile 
	 */
	def findUserDocuments(userId: Long): List[Document] = DB.withConnection { 
		implicit c => 
		SQL("""
			select d.* 
			from Document d 
			join UserDocument ud ON d.documentId = ud.documentId 
			where ud.userId = {userId}
			""")
		.on('userId -> userId) 
		.as(document *)
	}
	
	/**
		Retrieve all the documents in the user profile 
	 */
	def findUserDocument(userId: Long, documentId: Long): Option[Document] = DB.withConnection { 
		implicit c => 
		SQL("""
			select d.* 
			from Document d 
			join UserDocument ud ON d.documentId = ud.documentId 
			where ud.userId = {userId}
		    and ud.documentId = {documentId}
			""")
		.on(('userId -> userId), ('documentId -> documentId)) 
		.as(document *)
		.headOption
	}
}