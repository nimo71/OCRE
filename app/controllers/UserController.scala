package controllers

import play.api.mvc._

object UserController extends Controller {
  
  	import views._
  	
  	def profile() = Action { implicit request => 
  		Ok(html.profile())
  	}
}