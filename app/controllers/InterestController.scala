package controllers

import play.api._
import play.api.libs.json._
import play.api.mvc._  	
  	
object InterestController extends Controller {
  
  	def interested(id: String) = Action { implicit request => 
  		Logger.info("Interested, id="+ id +", url="+ request.queryString("u")(0))
  		Ok(Json.toJson(Map("result"->"Ok")))
  	}
  	
  	def saveInterests() = Action { implicit request =>
  		Logger.info("saveInterests()")
  		Ok("saveInterests()")
  	}
}