package jp.korenani.korenani.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.korenani.korenani.config.MyUserDetailsService;
import jp.korenani.korenani.entities.Answer;
import jp.korenani.korenani.repository.AnswerRepository;
import jp.korenani.korenani.repository.JPASignUpRepository;

@Controller
 public class AnswerController {

	
	@Autowired
	JPASignUpRepository jpaSignUpRepo;
 
	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		  if (principal instanceof UserDetails) return ((UserDetails)
		  principal).getUsername();
		 
		return principal.toString(); 
	}
	
 	@Autowired
 	AnswerRepository AnswerRepo;
 	
	@PostMapping(value="add-answer",consumes = "application/json")
	public ResponseEntity<String> PostAnswerAdd(@AuthenticationPrincipal OAuth2User principal, @RequestBody Answer answer,ModelMap model)
	{
			String email="";
 		   
			if(principal!=null)
			{
 			email=principal.getAttribute("email").toString();
			}
			else if(getLoggedInUserName()!=null)
			{
 				email=getLoggedInUserName();
	  		}
		   
			
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		   LocalDateTime now = LocalDateTime.now();
 		   answer.setDate(dtf.format(now));
  		   Optional<String> userProfileName=jpaSignUpRepo.getUserProfileNameByUsername(email);
  		   answer.setUserprofilename(userProfileName.get());

 		   AnswerRepo.save(answer);
 		   return new ResponseEntity<>("result successful result", 
				HttpStatus.OK);
	}
	
	
}
