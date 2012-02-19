package controllers

import play.api._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import play.api.data.Form._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
  	

object UserController extends Controller {
  
  	import views._
  	import models._
  	
  	def profile() = Action { implicit request => 
  		Ok(html.profile())
  	}
}