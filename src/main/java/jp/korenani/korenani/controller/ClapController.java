package jp.korenani.korenani.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.korenani.korenani.entities.ClapsOfAnswer;
import jp.korenani.korenani.entities.ClapsOfQuestion;
import jp.korenani.korenani.entities.DislikesOfAnswer;
import jp.korenani.korenani.entities.DislikesOfQuestion;
import jp.korenani.korenani.repository.ClapsAnswerRepository;
 import jp.korenani.korenani.repository.ClapsQuestionRepository;
import jp.korenani.korenani.repository.DislikesAnswersRepository;
import jp.korenani.korenani.repository.DislikesQuestionRepository;
 

@Controller
@RequestMapping("/discuss")
public class ClapController {

	@Autowired
	ClapsQuestionRepository clapsQuesstionRepo;
	
	@Autowired
	ClapsAnswerRepository clapsAnswerRepo;
	
	@Autowired
	DislikesQuestionRepository dislikesQuestionRepo;
	
	@Autowired
	DislikesAnswersRepository dislikesAnswerRepo;
	
	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		  if (principal instanceof UserDetails) return ((UserDetails)
		  principal).getUsername();
		 
		return principal.toString(); 
	}
	
	@PostMapping(value = "add-clap-question")
	public ResponseEntity<String> AddClapost(@RequestBody Integer qid,@AuthenticationPrincipal OAuth2User principal,  Model model)
	{
 		
		   
		   //---------------Getting Username---------------------
		   String email="";
 		   
			if(principal!=null)
			{
 			email=principal.getAttribute("email").toString();
			}
			else if(getLoggedInUserName()!=null)
			{
 				email=getLoggedInUserName();
	  		}
		 
			
			
			
			
			
			
		//------------checking if user has clapped in past 24 hours----------------
		    String datetimeRecorded=clapsQuesstionRepo.getDatetimeOfQuestionClapByUserProfileNameAndQid(qid, email);
 			if(datetimeRecorded!=null)
			{
				// if datetimeRecorded is not null that means that user has already clapped on this, and so he will be updating the date 
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	 			LocalDateTime datetimeparsed= LocalDateTime.parse(datetimeRecorded,dtf1);
	 			
			if(datetimeparsed.plusHours(24).isAfter(LocalDateTime.now()))
			{
				
 				model.addAttribute("clapbeforetimeerror","Thank you for your feedback, But you can only react once in 24 hours");
				 return new ResponseEntity<>("Thank you for your feedback, But you can only react once in 24 hours", 
						   HttpStatus.INTERNAL_SERVER_ERROR);
				 
			}
			else 
			{
				// before 24 hours
				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				clapsQuesstionRepo.updateClapsByQid(qid, email,dtf2.format(now));
				 		 return new ResponseEntity<>("Thank you for your feedback", 
						   HttpStatus.OK);	
			}
			}
			
			else 
			{
				ClapsOfQuestion newclap=new ClapsOfQuestion();
				
				newclap.setUserprofilename(email);
				newclap.setQid(qid);
				newclap.setClaps(1);
				 
				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				newclap.setDatetime(dtf2.format(now));
 				clapsQuesstionRepo.save(newclap);
				 return new ResponseEntity<>("Thank you for your feedback", 
						   HttpStatus.OK);	
			}
			
			}
 			
//			else {
//				
//				// if datetimeRecorded is null that means a new record will be added 
//				System.out.println("datetimeRecorded  I S N U L  L ");
//				ClapsOfQuestion newclap=new ClapsOfQuestion();
//				
//				newclap.setUsername(email);
//				newclap.setQid(qid);
//				newclap.setClaps(1);
//				
//				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//				LocalDateTime now = LocalDateTime.now();
//				newclap.setDatetime(dtf2.format(now));
//				System.out.println("dtf.format(now)) "+dtf2.format(now));
//				clapsQuesstionRepo.save(newclap);
//				 return new ResponseEntity<>("Thank you for your feedback", 
//						   HttpStatus.OK);	
//			}
			
			
		//----------------checking if the same user has already clapped or not--------------------
		/*
		 * if(clapsQuesstionRepo.isClapsAlreayExistingForQuestion(qid,email)==1) {
		 * DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		 * LocalDateTime now = LocalDateTime.now();
		 * clapsQuesstionRepo.updateClapsByQid(qid, email,dtf2.format(now));
		 * System.out.println("E X I S T I N G "+ qid); } else {
		 * System.out.println("N o n E X I S T I N G" + qid); ClapsOfQuestion
		 * newclap=new ClapsOfQuestion();
		 * 
		 * newclap.setUsername(email); newclap.setQid(qid); newclap.setClaps(1);
		 * 
		 * DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		 * LocalDateTime now = LocalDateTime.now();
		 * newclap.setDatetime(dtf2.format(now));
		 * System.out.println("dtf.format(now)) "+dtf2.format(now));
		 * clapsQuesstionRepo.save(newclap); }
		 */

	
	
	
	@PostMapping(value = "add-clap-answer")
	public ResponseEntity<String> AddClapostAnswer(@RequestBody Integer aid, @AuthenticationPrincipal OAuth2User principal,Model model)
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
		 
			
			
			//----------------checking if user has clapped in past 24 hours--------------------
		    String datetimeRecorded=clapsAnswerRepo.getDatetimeOfAnswerClapByUserProfilenameAndAid(aid, email);
 			if(datetimeRecorded!=null)
			{
				// if datetimeRecorded is not null that means that user has already clapped on this, and so he will be updating the date 
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	 			LocalDateTime datetimeparsed= LocalDateTime.parse(datetimeRecorded,dtf1);
	 			
			if(datetimeparsed.plusHours(24).isAfter(LocalDateTime.now()))
			{
				
 				model.addAttribute("clapbeforetimeerror","Thank you for your feedback, But you can only react once in 24 hours");
				 return new ResponseEntity<>("Thank you for your feedback, But you can only react once in 24 hours", 
						   HttpStatus.INTERNAL_SERVER_ERROR);
				 
			}
			else 
			{
				// before 24 hours
				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				clapsAnswerRepo.updateClapsByAid(aid, email,dtf2.format(now));
			 		 return new ResponseEntity<>("Thank you for your feedback", 
						   HttpStatus.OK);	
			}
			}
			
			else 
			{
				//never clapped
				ClapsOfAnswer newclap=new ClapsOfAnswer();
				
				newclap.setUserprofilename(email);
				newclap.setAid(aid);
				newclap.setClaps(1);
				
				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				newclap.setDatetime(dtf2.format(now));
 				clapsAnswerRepo.save(newclap);
				return new ResponseEntity<>("Thank you for your feedback", 
						   HttpStatus.OK);
			}
			
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		 System.out.println("POST REQUEST MADE "+aid);
//			//setting email field
//
//		   String email="";
//		   System.out.println("getLoggedInUserName "+ getLoggedInUserName().length());
//		   
//			if(principal!=null)
//			{
//			System.out.println(" A T T R I B U T  E "+principal.getAttribute("email").toString());
//			email=principal.getAttribute("email").toString();
//			}
//			else if(getLoggedInUserName()!=null)
//			{
//				System.out.println(" L  O G G E D I N U S E R  N A M E I  S "+getLoggedInUserName());				email=getLoggedInUserName();
//	  		} 
//			
//			
//			
//		if(clapsAnswerRepo.isClapsAlreayExistingForAnswer(aid)==1)
//		{
//			clapsAnswerRepo.updateClapsByAid(aid);
//			System.out.println("E X I S T I N G "+ aid);
//		}
//		else {
//			System.out.println("N o n E X I S T I N G" + aid);
//			ClapsOfAnswer newclap=new ClapsOfAnswer();
//			newclap.setQid(aid);
//			newclap.setClaps(1);
//			newclap.setUsername(email);
//
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//			LocalDateTime now = LocalDateTime.now();
//			newclap.setDatetime(dtf.format(now));
//			
//			clapsAnswerRepo.save(newclap);
//		}
//		
//		 return new ResponseEntity<>("result successful result", 
//				   HttpStatus.OK);	
	
	
	@PostMapping(value = "add-dislike-question")
	public ResponseEntity<String> AddDislikeostQuestion(@RequestBody Integer qid, @AuthenticationPrincipal OAuth2User principal,Model model)
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
			
			
			//left here 25/sept
			//------------checking if user has clapped in past 24 hours----------------
			    String datetimeRecorded=dislikesQuestionRepo.getDatetimeOfQuestionDislikesByUserPriflieNameAndQid(qid, email);
 				if(datetimeRecorded!=null)
				{
					// if datetimeRecorded is not null that means that user has already clapped on this, and so he will be updating the date 
					DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		 			LocalDateTime datetimeparsed= LocalDateTime.parse(datetimeRecorded,dtf1);
		 			
				if(datetimeparsed.plusHours(24).isAfter(LocalDateTime.now()))
				{
					
 					model.addAttribute("clapbeforetimeerror","Thank you for your feedback, But you can only react once in 24 hours");
					 return new ResponseEntity<>("Thank you for your feedback, But you can only react once in 24 hours", 
							   HttpStatus.INTERNAL_SERVER_ERROR);
					 
				}
				else 
				{
					// before 24 hours
					DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					dislikesQuestionRepo.updateDislikesByQid(qid, email,dtf2.format(now));
			 		 return new ResponseEntity<>("Thank you for your feedback", 
							   HttpStatus.OK);	
				}
				}
				
				else 
				{
					DislikesOfQuestion newDislike=new DislikesOfQuestion();
					
					newDislike.setUserprofilename(email);
					newDislike.setQid(qid);
					newDislike.setDislikes(1);
					
					DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					newDislike.setDatetime(dtf2.format(now));
 					dislikesQuestionRepo.save(newDislike);
					 return new ResponseEntity<>("Thank you for your feedback", 
							   HttpStatus.OK);	
				}
	}
	
	
	@PostMapping(value = "add-dislike-answer")
	public ResponseEntity<String> AddDislikeostAnswer(@RequestBody Integer aid, @AuthenticationPrincipal OAuth2User principal,Model model)
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
		 
			
			
			
			
			//checking if the user is clapping on his own post
		/*
		 * if(email==) {
		 * 
		 * 
		 * }
		 */
			
			
			
			
			
			//----------------checking if user has clapped in past 24 hours--------------------
		    String datetimeRecorded=dislikesAnswerRepo.getDatetimeOfAnswerDislikesByUserPrifileNameAndAid(aid, email);
 			if(datetimeRecorded!=null)
			{
				// if datetimeRecorded is not null that means that user has already clapped on this, and so he will be updating the date 
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	 			LocalDateTime datetimeparsed= LocalDateTime.parse(datetimeRecorded,dtf1);
	 			
			if(datetimeparsed.plusHours(24).isAfter(LocalDateTime.now()))
			{
				
 				model.addAttribute("clapbeforetimeerror","Thank you for your feedback, But you can only react once in 24 hours");
				 return new ResponseEntity<>("Thank you for your feedback, But you can only react once in 24 hours", 
						   HttpStatus.INTERNAL_SERVER_ERROR);
				 
			}
			else 
			{
				// before 24 hours
				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				dislikesAnswerRepo.updateDislikesByAid(aid, email,dtf2.format(now));
			 	 return new ResponseEntity<>("Thank you for your feedback", 
						   HttpStatus.OK);	
			}
			}
			
			else 
			{
				//never clapped
				DislikesOfAnswer newDislike=new DislikesOfAnswer();
				
				newDislike.setUserprofilename(email);
				newDislike.setAid(aid);
				newDislike.setClaps(1);
				
				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				newDislike.setDatetime(dtf2.format(now));
 				dislikesAnswerRepo.save(newDislike);
				return new ResponseEntity<>("Thank you for your feedback", 
						   HttpStatus.OK);
			} 
	} 
}