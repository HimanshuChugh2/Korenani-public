package jp.korenani.korenani.controller;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import jp.korenani.korenani.config.ReCaptchaResponse;
import jp.korenani.korenani.entities.SignUpUserDetails;
 import jp.korenani.korenani.repository.JPASignUpRepository;

@Controller
 public class SignUpController {
	@Autowired
	JPASignUpRepository jpaSignUpRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	/*
	 * @GetMapping("/") public String signUp(SignUpUserDetails signUpUser,Model
	 * model) { SignUpUserDetails objDetails=new SignUpUserDetails();
	 * model.addAttribute("signUpUser",objDetails);
	 * 
	 * return "index"; }
	 */
	
	
//	@GetMapping("/sign-up")
//	 
//		public String signUp1(SignUpUserDetails signUpUser,Model model) {
//			SignUpUserDetails objDetails=new SignUpUserDetails();
//			model.addAttribute("signUpUser",objDetails);
//			
//			return "index";
//		
//	}
	
	
	 @PostMapping("/signup")
	   public String signUpPost1(@ModelAttribute("signUpUser") @Valid
	  SignUpUserDetails signUpUser , Errors errors, ModelMap model, @RequestParam (name="g-recaptcha-response") String captchaResponse)
	  
	  {
	  if (errors.hasErrors())
	  {
			/*
			 * SignUpUserDetails objDetails=new SignUpUserDetails();
			 * model.addAttribute("signUpUser",objDetails);
			 */
 		  model.addAttribute("validationerroroccoured","Error: Validation error, please try again with valid inputs");
 		  return "login";
	  }
	  
	  
	  Optional<SignUpUserDetails> signUpObj =
	  jpaSignUpRepository.findByUsername(signUpUser.getUsername());
 	  	if (signUpObj.isPresent())
	  		{
 		    model.addAttribute("message", "The e-mail: " +
		    signUpUser.getUsername() + " already exists, try with some other e-mail address");
 	  		return "login";		  
		  }

	  	else 
	  		{
	  		String url="https://www.google.com/recaptcha/api/siteverify";
	  		String params="?secret=6Ldn0LIZAAAAAGe8qHM-50cHEF0yQJmP59XJTnqJ&response="+captchaResponse;
	  		
	  		ReCaptchaResponse reCaptchaResponse=restTemplate.exchange(url+params, HttpMethod.POST,null,ReCaptchaResponse.class).getBody();
	  		
	  		if(reCaptchaResponse.isSuccess())
	  		{
	  			String userProfileName=signUpUser.getUsername().substring(0,signUpUser.getUsername().indexOf("@"));
 	  			 signUpUser.setUserProfileName(userProfileName);
	  			 jpaSignUpRepository.save(signUpUser); 
	 			 //model.addAttribute("user_profile_name",signUpUser.getUserProfileName());
	  			  model.put("SignUpSuccessMessage", "User added successfully " + signUpUser.getUserProfileName()+ ". Please proceed with Sign in "); 		
	  			
	  			  return "login";
			} 
	  		else
			 {
				  	  model.addAttribute("recaptchaerror","please verify reCAPTCHA");
					  return "login";  
			 }
	  		}
	  }	 
	}