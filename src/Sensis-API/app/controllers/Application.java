package controllers;

import java.util.ArrayList;

import models.People;
import models.SAPI_User;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
    
    public static Result index() {  
    	ArrayList<SAPI_User> userList = new ArrayList();
    	
    	//Test Data by hardcode
    	SAPI_User tony = new SAPI_User(1,"Tony", "2014-03-27", "s3417298@student.rmit.edu.au", 8, 5);
    	SAPI_User megha = new SAPI_User(2,"Megha", "2014-03-25", "s3423808@student.rmit.edu.au", 11, 3);
    	SAPI_User halini = new SAPI_User(2,"Hanili", "2013-02-13", "s3395127@student.rmit.edu.au", 5, 2);
    	userList.add(tony);
    	userList.add(megha);
    	userList.add(halini);
       
        return ok(views.html.index.render("DashBoard",userList));
    } 
    
}
