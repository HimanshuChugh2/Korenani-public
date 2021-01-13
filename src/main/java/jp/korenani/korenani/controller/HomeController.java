package jp.korenani.korenani.controller;

 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.korenani.korenani.entities.SignUpUserDetails;
 
@Controller
public class HomeController {
	
 
	
	
	  @GetMapping("/index") public String funindex() { return "index"; }
	  
	  @GetMapping("/")
	    public String root(Model model) {
		  
		  
		  SignUpUserDetails objDetails=new SignUpUserDetails();
		  model.addAttribute("signUpUser",objDetails);
	        return "accessible";
	    }

	  
	  @GetMapping("/accessible")
	  public String accessible()
	  {
		  return "accessible";
	  }
	  
	/*
	 * @GetMapping("/login") public String login(Model model) { return "login"; }
	 */
	/*
	 * @GetMapping("/user") public String userIndex() { return "user/index"; }
	 */
	
}
