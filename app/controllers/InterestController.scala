package controllers

import play.api._
import play.api.libs.json._
import play.api.mvc._  	
  	
object InterestController extends Controller {
  
  	import models.Document
  	import models.User
  	import services.Cypher
  
  	def interested(id: String) = Action { implicit request => 
  		Logger.info("Interested")
  		
  		val url = request.queryString("u")(0)
  		Logger.info("Interested, id="+ id +", url="+ url)
  		
  		val doc : Option[Document] = Document.findByUrl(url).orElse {  
  			Document.create(url)
  			Document.findByUrl(url)
  		}

		doc match {
			case Some(d) => {
				val userId = Cypher.decode(id)
  				Logger.info("associating documentId="+ d.id +", and userId="+ userId)
  				if (Document.findUserDocument(userId.toLong, d.id) == None) {
  					User.addDocumentToUser(userId.toLong, d.id)
  					Logger.info("user saved document: " + d.url)
  				}
  				else {
  				  Logger.info("user already saved document: " + d.url)
  				}
  				Ok(Json.toJson(Map("result"->"saved")))
  			}
			case None => 
				Ok(Json.toJson(Map("result"->"save failed")))
		}
  	}
  	
  	def saveInterests() = Action { implicit request =>
  		Logger.info("saveInterests()")
  		Ok("saveInterests()")
  	}
}