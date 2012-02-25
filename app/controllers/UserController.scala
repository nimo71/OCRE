package controllers

import play.api._
import play.api.libs._
import play.api.mvc._

object UserController extends Controller {
  
  	import views._
  	
  	def profile() = Action { implicit request => 
  		Logger.info(request.session.toString)
  		Ok(html.profile(Crypto.sign(request.session("userId"))))
  	}
}