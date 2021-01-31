package jp.korenani.korenani.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.korenani.korenani.config.MyUserDetailsService;
import jp.korenani.korenani.entities.Question;
import jp.korenani.korenani.repository.QuestionRepository;

@Controller
@SessionAttributes({"useremail"})

public class QuestionContoller {

	//make answer functionality, the questions that are posted, if someone wants to add an answer there should be a way to do that
	@Autowired
	QuestionRepository QuestionRepo;
	
	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // getting the
																									// principal
		  
		
		  if (principal instanceof UserDetails) return ((UserDetails)
		  principal).getUsername();
		 
		return principal.toString();
	}
	
	
 	@GetMapping("/ask-question")
	public String askQuestion(Model model)
	{
		
		Question question=new Question();
		model.addAttribute("question",question);
		
		return "ask-question";
	}
	
	
	@PostMapping("/ask-question")
	public ResponseEntity<String> questionPost(@RequestBody Question question, Model model,ModelMap modelMap)
	{
//		   String email="";
//		   System.out.println("u s e r n a m e quescontr  "+model.getAttribute("useremail"));
//		   System.out.println("getLoggedInUserName "+ getLoggedInUserName().length());
//
//		if(getLoggedInUserName().length()>150)
//			{
//				// this means that this is a social login, so will use useremail session 
//			email=(String)model.getAttribute("useremail");
//			}
//		else {
//			email=getLoggedInUserName();
//			}

 		
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		   LocalDateTime now = LocalDateTime.now();
 		   question.setDate(dtf.format(now));
		   
   		   
		  // System.out.println("String email is   "+email);

		   question.setUserprofilename(question.getUserprofilename());
		   QuestionRepo.save(question);
		   return new ResponseEntity<>("result successful result", 
				   HttpStatus.OK);	
	}
}