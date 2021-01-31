package jp.korenani.korenani.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import cats.effect.internals.IOAppCompanionPlatform.Context;

import java.util.Optional;

import jp.korenani.korenani.config.ReCaptchaResponse;
import jp.korenani.korenani.entities.SignUpUserDetails;
import jp.korenani.korenani.repository.JPASignUpRepository;
import jp.korenani.korenani.repository.PasswordReposoitory;

@Controller
public class PasswordController {
	
	private String email = "";
	private String name = "";
	
 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	JPASignUpRepository SignUpjpaRepository;
	
	@Autowired
	PasswordReposoitory passwordReposoitory;
	
	@GetMapping("/enter-existing-password")
	public String enterExisting(Model model)
	{
		
		return "enter-existing-password";
	}
	
	@GetMapping("/enter-password")
	public String enterpassword(@AuthenticationPrincipal OAuth2User principal,Model model)
	{
		
		// if the user is signing up for the first time then return to enter password 
		// if the user has already signed up, then return to the homepage/restricted 
		
 		email = principal.getAttribute("email");
		name = principal.getAttribute("name");

		
 		if (SignUpjpaRepository.findByUsername(email).isPresent())
			{
			/* 			
			 * 			model.addAttribute("message", "existingUser");
			 * 			if their email is in database, that means that they have logged 						in, in past.
			 */			
			Optional<String> user_profile_name = SignUpjpaRepository.getUserProfileNameByUsername(email);
			model.addAttribute("user_profile_name",user_profile_name.get());
 			
			model.addAttribute("email", email);
			
			return "restricted";
			}
		else 
			{
			//the user has logged in using oauth2 so set the email as verified
			
			SignUpUserDetails newuser=new SignUpUserDetails();
			newuser.setName(name);
			newuser.setUsername(email);
			
			newuser.setUserProfileName(String.valueOf(newuser.getId()));
 			newuser.setEmailverified(true);
			SignUpjpaRepository.save(newuser);
			// id of the entity object will be created after saving the entity, so now will update the user_profile_name to be same as id
 				
			SignUpjpaRepository.updateUserProfileNameById(String.valueOf(newuser.getId()), newuser.getId());
			model.addAttribute("signUpUser1",newuser);
			model.addAttribute("user_profile_name",newuser.getId());

			return "enter-password";
			}
		 
	}
	
	@PostMapping("/enter-password")
	public String enterpasswordPost(@ModelAttribute("signUpUser1") SignUpUserDetails newUser,@AuthenticationPrincipal OAuth2User principal,Model model)
	{
 
		String passwordfromdatabase= passwordReposoitory.isPasswordValid(email);
		
		if(passwordfromdatabase.equals(newUser.getPassword()))
		{
 			return "restricted";
			
		}
		else {
 			//password is not matching but still its letting the user log in
			principal=null;
			return "redirect:/login";
		}
 	}
	/*
	 * @GetMapping("/save-password") public void
	 * savepasswordGet(@AuthenticationPrincipal OAuth2User principal, Model model) {
	 * 
	 * System.out.println("  I  N  SAVE PASSWORD GET ");
	 * 
	 * 
	 * }
	 */
	
	@PostMapping("/save-password")
	public String savepassword(@ModelAttribute("signUpUser1") SignUpUserDetails newUser,Model model,@RequestParam (name="g-recaptcha-response") String captchaResponse)
	{	 
		String url="https://www.google.com/recaptcha/api/siteverify";
  		String params="?secret=6Ldn0LIZAAAAAGe8qHM-50cHEF0yQJmP59XJTnqJ&response="+captchaResponse;
  		
  		ReCaptchaResponse reCaptchaResponse=restTemplate.exchange(url+params, HttpMethod.POST,null,ReCaptchaResponse.class).getBody();
  		
  		if(reCaptchaResponse.isSuccess())
  		{ 
 		newUser.setUserProfileName("null");
		newUser.setUsername(email);
		newUser.setPassword(newUser.getPassword());
		newUser.setEmailverified(true);
		SignUpjpaRepository.updateUsernameByUsername(newUser.getPassword(), email);
		//SignUpjpaRepository.save(newUser);
		model.addAttribute("emailverified", "The email id "+newUser.getUsername()+ " has been verified");
		return "restricted";
	}
  		else 
  		{
  			 model.addAttribute("message","please varify reCAPTCHA");
  			 return "enter-existing-password";
		}
}
}
