package controllers

import play.api._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import play.api.data.Form._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
  	

object Application extends Controller {
  
  	import views._
  	import models._
  
  	val email = text verifying pattern(
  		"""\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}\b""".r,
  		"constraint.email",
  		"error.invalid.email"
	)
  
  	/**
  		Log in form backing object
  	*/
  	val logInForm = Form(
    	tuple(
      		"email" -> email,
      		"password" -> text
    	) verifying (
    		Messages("invalid.login.credentials"), 
    		result => result match {
  				case (email, password) => 
  					!User.findByEmail(email).forall(_.password != password)
  			}
  		)
  	)
  
  	/**
  		Show the log in page
  	*/
  	def index() = Action { implicit request =>
		Ok(html.index(logInForm))
    }
    
    /**
    	Process the login form
    */
    def logIn() = Action { implicit request =>
    	logInForm.bindFromRequest.fold(
      		formWithErrors => BadRequest(html.index(formWithErrors)),
      		result => result match { 
      			case (email, password) => Ok(email)}
      		)
    }  
    
    /** 
    	Registration form backing object
    */
    val registrationForm = Form(
    	tuple(
    		"email" -> tuple (
    			"main" -> email,
    			"confirm" -> text
    		).verifying (
    			Messages("values.dont.match", "Email", "Confirm Email"), 
    			result => result match {
    				case (main, confirm) => main == confirm
    			}
    		),
    		"password" -> tuple (
      			"main" -> text.verifying(
      				Messages("error.password", 4, 12), 
      				s => { s.length >= 4 && s.length <= 12 } ),
      			"confirm" -> text
      		).verifying (
      		    Messages("values.dont.match", "Password", "Confirm Password"), 
    			result => result match {
    				case (main, confirm) => main == confirm
    			}
      		)
    	).verifying (
    		Messages("user.already.exists"), 
    		result => result match {
    			case ((email, _), (_, _)) => 
    				!User.findByEmail(email).isDefined
    		}
    		
    	)
    ) 
    
    /**
    	Show the registration form
    */
    def registration() = Action { implicit request =>
    	Ok(html.registration(registrationForm))
    }
    
    /**
    	Process the registration form
    */
    def register() = Action { implicit request => 
    	registrationForm.bindFromRequest.fold(
	    	// Form has errors, redisplay it
      		errors => BadRequest(html.registration(errors)),
      
      		// We got a valid User value, display the summary
      		user => {
      			// save the new user to the database
      			User.create(user._1._1, user._2._1)
      			Redirect(routes.Application.index)
      				.flashing("message" -> Messages("log.in.after.registration"))
      		}
    	)
    	
    }
    
  	/**
  		Log in form backing object
  	*/
  	val forgottenPasswordForm = Form(
    		"email" -> email
  	)    
    
    /**
    	Show the forgotten password form
     */
    def forgottenPassword() = Action { implicit request => 
    	Ok(html.forgottenPassword(forgottenPasswordForm))
    }
    
    /**
    	Process the forgotten password form
     */
    def resetPassword() = Action { implicit request => 
    	forgottenPasswordForm.bindFromRequest.fold(
	    	// Form has errors, redisplay it
      		errors => BadRequest(html.forgottenPassword(errors)),
      
      		// We got a valid User value, display the summary
      		user => {
      			// save the new user to the database
      			Redirect(routes.Application.index)
      				.flashing("message" -> Messages("password.reset"))
      		}
      	)
    }
    
}