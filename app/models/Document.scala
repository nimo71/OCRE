package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

class Document(id: Long, url: String)

object Document {
	
	val document = {
		get[Long]("id") ~ 
  		get[String]("url") map {
    		case id~url => Document(id, url)
  		}
	}
	
	/**
		Add a new document to the database
	 */	
	def create(url: String) = DB.withConnection { implicit c =>
   		SQL("insert into Document (url) values ({url})")
   			.on(('url -> url))
   			.executeUpdate()
	}
	
	/**
		Associate a document with a user
	*/
	def addDocumentToUser(documentId: Long, userId: Long) = 
		DB.withConnection { implicit c => 
			SQL("insert into UserDocument (userId, documentId) values ({userId, documentId})")
				.on(('userId -> userId), ('documentId -> documentId))
				.executeUpdate()
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
}