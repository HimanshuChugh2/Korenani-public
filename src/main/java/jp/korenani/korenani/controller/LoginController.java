package jp.korenani.korenani.controller;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import io.getquill.ast.If;
import jp.korenani.korenani.config.ReCaptchaResponse;
import jp.korenani.korenani.entities.SignUpUserDetails;
import jp.korenani.korenani.repository.JPASignUpRepository;

@SessionAttributes("{useremail,userprofilename}")
 @Controller
public class LoginController {	
	
	
	@Autowired
	JPASignUpRepository signUpRepo;
	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // getting the
																									// principal

		
		  if (principal instanceof UserDetails) return ((UserDetails)
		  principal).getUsername();
		 
		return principal.toString();
	}

	@Autowired
	JPASignUpRepository SignUpjpaRepository;

	private String email = "";
	private String name = "";

	/*
	 * @GetMapping("/login") public String login() { return "login"; }
	 */
	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public String loginPost(@RequestParam String email, ModelMap
	 * model) {
	 * 
	 * model.put("name", username); model.put("username", (String)
	 * model.get("name"));
	 * 
	 * String xString=email; return xString; }
	 */
	/*
	 * @GetMapping("/login") public String loginstring(Model model) {
	 * 
	 * SignUpUserDetails objDetails=new SignUpUserDetails();
	 * model.addAttribute("signUpUser",objDetails);
	 * 
	 * return "login"; }
	 */
 	/*
	 * @GetMapping("/login-error") public String loginError(Model model) {
	 * model.addAttribute("loginError", true); return "login"; }
	 */
 	/*
	 * @GetMapping("/login") public String loginGet(ModelMap model) {
	 * model.put("username", getLoggedInUserName());
	 * 
	 * String name = (String) model.get("name"); if (name == null) {
	 * model.put("logoutmessage", "You have been logged out"); }
	 * System.out.println("VALUE in SESSION is login " + name); return "index"; }
	 */
 	/*
	 * @PostMapping("/login")
	 * 
	 * @RequestMapping(value = "/login", method = RequestMethod.POST) public String
	 * loginPost( ModelMap model) { model.put("username", (String)
	 * model.get("name")); return "restricted"; }
	 */

	
	 
	
	
	@GetMapping("/login")
	public String loginGet(Model model,@AuthenticationPrincipal OAuth2User principal) {

		SignUpUserDetails objDetails=new SignUpUserDetails();
	    model.addAttribute("signUpUser",objDetails);

	    
		return "login";
	}
	
	
	
	
	@GetMapping("/user")
	@ResponseBody
	public /*Map<String, Object>*/ String user(@AuthenticationPrincipal OAuth2User principal, Model model) {
		
		
		
		
		/*if(principal!=null) 
		{*/
 		email = principal.getAttribute("email");
		name = principal.getAttribute("name");
		
 
 
		//System.out.println("N  a m e  IS:   " + name);
		//Collections.singletonMap("name", principal.toString());
		/*
		 * return "restricted"; }
		 */
		/*
		 * return Collections.singletonMap("email", principal.getAttribute("email"));
		 */	
		model.addAttribute("useremail",email);
		
		/* return Collections.singletonMap("name", principal.getAttribute("email")); */
		return "login";
	}

	//previously return type of this method was "Map<String, Object>"
	
//	@GetMapping("/restricted")
//	@ResponseBody
//	public String loginConfiguration(@AuthenticationPrincipal OAuth2User principal) {
//		email = principal.getAttribute("email");
//		name = principal.getAttribute("name");
//		SignUpUserDetails newUser = new SignUpUserDetails();
//
//		if (SignUpjpaRepository.findByUsername(email) != null) 
//		{
//
//			return "This email is already registered with an account, please try again with another email";
//		
//		} 
//		else 
//		{
//			newUser.setUsername(email);
//			newUser.setfullname(name);
//			SignUpjpaRepository.save(newUser);
//		}
//
//		return "Welcome";
//	}

	
	//why this function is there?
	//ans: --> this function will be called from ajax as a post, and i will use this function to save the value of  userprofilename in a session object when user has logged in using formLogin(). when use logs in using oAuth2 then session will be created at /user
	
	// this function is not being called on the first time, but when user is already logged in then it can be called, so its of no use
	@PostMapping(path="/loginsessionsave", consumes = "application/json")
	public ResponseEntity<String> postLoginForRetainingSessionValue(@RequestBody String username ,Model model)
	{
 		
		Optional<String> valOptional=signUpRepo.getUserProfileNameByUsername(username);
 		//not able to populate this model
		model.addAttribute("userprofilename",valOptional.get());
		model.addAttribute("userprofilename","Hello");

 		return new ResponseEntity<>("result successful result", 
				   HttpStatus.OK);
	}
	@GetMapping("/restricted")
	public String restricted(Model model,@AuthenticationPrincipal OAuth2User principal)
	{ 		
  		Optional<String> user_profile_name = SignUpjpaRepository.getUserProfileNameByUsername(getLoggedInUserName());
		model.addAttribute("user_profile_name",user_profile_name.get());
	 
 
		return "restricted";
	}
}

