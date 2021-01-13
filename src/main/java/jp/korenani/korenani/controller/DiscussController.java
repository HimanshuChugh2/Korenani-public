package jp.korenani.korenani.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.korenani.korenani.entities.Answer;
import jp.korenani.korenani.entities.AnswerClapCountAndAidWrapper;
import jp.korenani.korenani.entities.AnswerDatetimeAndAidWrapperInterface;
import jp.korenani.korenani.entities.AnswerDislikesCountAndAidWrapper;
import jp.korenani.korenani.entities.AnswerisClappedIn24HourAndAidWrapperClass;
import jp.korenani.korenani.entities.Question;
import jp.korenani.korenani.repository.AnswerRepository;
import jp.korenani.korenani.repository.ClapsAnswerRepository;
import jp.korenani.korenani.repository.ClapsQuestionRepository;
import jp.korenani.korenani.repository.DiscussRepository;
import jp.korenani.korenani.repository.DislikesAnswersRepository;
import jp.korenani.korenani.repository.DislikesQuestionRepository;
import jp.korenani.korenani.repository.QuestionRepository;

@Controller
@RequestMapping("/discuss")
public class DiscussController {
  
	@Autowired
	DiscussRepository discussrepo;
	
	@Autowired
	QuestionRepository QuestionRepo;
	
	@Autowired
	AnswerRepository AnswerRepo;
	
	@Autowired
	ClapsAnswerRepository clapsAnswerRepo;

	@Autowired
	ClapsQuestionRepository clapsQuestionRepo;
	
	@Autowired
	DislikesQuestionRepository dislikesQuestionRepo;
	
	@Autowired
	DislikesAnswersRepository dislikesAnswersRepo;
	
	
	
	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // getting the principal
		  
		
		  if (principal instanceof UserDetails) return ((UserDetails)
		  principal).getUsername();
		 
		return principal.toString();
	}
	
	@GetMapping
	public String returndiscuss(@AuthenticationPrincipal OAuth2User principal,Model model,ModelMap modelMap) 
	{
		if(principal!=null)
		{
		System.out.println(" A T T R I B U T  E "+principal.getAttribute("email").toString());
		}
		if(getLoggedInUserName()!=null)
		{
			System.out.println(" L O G G E D I N U S E R  N A M E I  S "+getLoggedInUserName());
  		}
		// show all questions recently posted
		 List<Object[]> l= discussrepo.getalldata();
		// System.out.println(l.get(2)[4]);
		 model.addAttribute("listy",l);
		 return "discuss";
	}
	
	 
	@GetMapping("{id}")
	public String discussId(@PathVariable("id") int id,@AuthenticationPrincipal OAuth2User principal, Model model) throws JsonProcessingException
	{
//		String email="";
//		   System.out.println("getLoggedInUserName "+ getLoggedInUserName().length());
//		   
//			if(principal!=null)
//			{
//			System.out.println(" A T T R I B U T  E "+principal.getAttribute("email").toString());
//			email=principal.getAttribute("email").toString();
//			}
//			else if(getLoggedInUserName()!=null)
//			{
//				System.out.println(" L  O G G E D I N U S E R  N A M E I  S "+getLoggedInUserName());	
//				email=getLoggedInUserName();
//	  		}
		 
		
		   String email="";
		   //System.out.println("getLoggedInUserName "+ getLoggedInUserName().length());
		   
			if(principal!=null)
			{
			//System.out.println(" A T T R I B U T  E "+principal.getAttribute("email").toString());
			email=principal.getAttribute("email").toString();
			}
			else if(getLoggedInUserName()!=null)
			{
				//System.out.println(" L  O G G E D I N U S E R  N A M E I  S "+getLoggedInUserName());	
				email=getLoggedInUserName();
	  		}
		
		//How to let the user know beforehand that you have clapped in last 24 hours
 		//------------checking if user has clapped in past 24 hours----------------
	    String datetimeRecorded=clapsQuestionRepo.getDatetimeOfQuestionClapByUserProfileNameAndQid(id, email);
		System.out.println("------------datetime recorded "+datetimeRecorded);
		if(datetimeRecorded!=null)
		{
			// if datetimeRecorded is not null that means that user has already clapped on this, and so he will be updating the date 
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
 			LocalDateTime datetimeparsed= LocalDateTime.parse(datetimeRecorded,dtf1);
 			
		if(datetimeparsed.plusHours(24).isAfter(LocalDateTime.now()))
		{ 	
			//System.out.println(" I S A F T E R ");
			model.addAttribute("clappedOnQuestionIn24Hours",1);	 
		}
		
		}
		else {
			//System.out.println(" I S B E F O R E  ");

			model.addAttribute("clappedOnQuestionIn24Hours",0);
		}
		 
		
		//getting count of clap of the question
		System.out.println("-------------------");
		Integer clapCountOfQuestion= clapsQuestionRepo.getClapCountByQid(id);
 
		 if(clapCountOfQuestion==null)
		 {
				model.addAttribute("clapofquestion",0);
		 }
		 else 
		 {
			 	model.addAttribute("clapofquestion",clapCountOfQuestion);
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		//How to let the user know beforehand that you have disliked in last 24 hours
	 		//------------checking if user has clapped in past 24 hours----------------
		    String datetimeRecordedOfDislikes=dislikesQuestionRepo.getDatetimeOfQuestionDislikesByUserPriflieNameAndQid(id, email);
			System.out.println("------------datetime recorded "+datetimeRecordedOfDislikes);
			if(datetimeRecordedOfDislikes!=null)
			{
				// if datetimeRecorded is not null that means that user has already clapped on this, and so he will be updating the date 
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	 			LocalDateTime datetimeparsed= LocalDateTime.parse(datetimeRecordedOfDislikes,dtf1);
	 			
			if(datetimeparsed.plusHours(24).isAfter(LocalDateTime.now()))
			{ 	
				//System.out.println(" I S A F T E R ");
				model.addAttribute("dislikedOnQuestionIn24Hours",1);	 
			}
			
			}
			else {
				//System.out.println(" I S B E F O R E  ");

				model.addAttribute("dislikedOnQuestionIn24Hours",0);
			}
			
			
			
			
			//getting count of clap of the question
			System.out.println("-------------------");
			Integer dislikesCountOfQuestion= dislikesQuestionRepo.getDislikesCountByQid(id);
	 
			 if(dislikesCountOfQuestion==null)
			 {
					model.addAttribute("dislikescountofquestion",0);
			 }
			 else 
			 {
				 	model.addAttribute("dislikescountofquestion",dislikesCountOfQuestion);
			 }

		 
		 
		 
		 
		 
		 
		 
		 
		 
		 //getting question data
		Question question=QuestionRepo.getById(id);
		//System.out.println(question.getuserprofilename());
		model.addAttribute("questiondata",question);
		
		
		
		
		
		//getting list of answer written for a ques
 		List<Answer> Allanswer=AnswerRepo.GetByQid(id);
 		ObjectMapper mapper = new ObjectMapper();
 		String newJsonData = mapper.writeValueAsString(Allanswer);
 		//System.out.println("newJsondata "+newJsonData);
   		model.addAttribute("answerdata",newJsonData);

   		
   		
   		//getting all the answer ids of all the answer retrieved, this ids will be used to get the claps associated with each answer
 		List<Integer> listofaid =new ArrayList<Integer>();
		System.out.println("list size is "+ Allanswer.size());

 		for (int i = 0; i < Allanswer.size(); i++) {
 			listofaid.add(Allanswer.get(i).getId());
 			//System.out.println("adding "+Allanswer.get(i).getId());
		}
 		
 		
 		// interfaceAnswerDatetimeAndAidWrapperInterface can only be used to get the data/to contain the data received from @query, i will have to put that data in a class if i want to manipulate the data received from @query, because i can not manipulate data of interface directly
 		
 		//getting ansId and datetime of last clap done by "username"
 	    List<AnswerDatetimeAndAidWrapperInterface> listOfdatetimeAndAidOfAnswerByThisUser= clapsAnswerRepo.getDatetimeAndAidByAidListAndUserProfilename(listofaid,email);
 	    
 	    List<AnswerisClappedIn24HourAndAidWrapperClass> IsAlreadyClappedList= new ArrayList<AnswerisClappedIn24HourAndAidWrapperClass>();
 	    for(int i=0;i<listOfdatetimeAndAidOfAnswerByThisUser.size();i++)
 	    {
 	    	
 	    	DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
 			LocalDateTime datetimeparsedForAnswerClap= LocalDateTime.parse(listOfdatetimeAndAidOfAnswerByThisUser.get(i).getDatetime(),dtf1);
 			
 				if(datetimeparsedForAnswerClap.plusHours(24).isAfter(LocalDateTime.now()))
 				{
 					//setting to 1 if the user has clapped in last 24 hours
 					AnswerisClappedIn24HourAndAidWrapperClass newobj=new AnswerisClappedIn24HourAndAidWrapperClass();
 					newobj.setAid(listOfdatetimeAndAidOfAnswerByThisUser.get(i).getAid());
 					newobj.setIsClappedIn24Hour(1);
 					IsAlreadyClappedList.add(newobj);
  				}
 	    }
 	    
 	    
 	    
 	    
 	    
 	    
 	    
 	    
 	    
 	 //getting ansId and datetime of last dislike done by "username"
 	    List<AnswerDatetimeAndAidWrapperInterface> listOfdatetimeAndAidOfAnswerByThisUser2= dislikesAnswersRepo.getDatetimeAndAidByAidListAndUserProfileName(listofaid,email);
 	    
 	    List<AnswerisClappedIn24HourAndAidWrapperClass> IsAlreadyDislikedList= new ArrayList<AnswerisClappedIn24HourAndAidWrapperClass>();
 	    for(int i=0;i<listOfdatetimeAndAidOfAnswerByThisUser2.size();i++)
 	    {
 	    	DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
 			LocalDateTime datetimeparsedForAnswerDislikes= LocalDateTime.parse(listOfdatetimeAndAidOfAnswerByThisUser2.get(i).getDatetime(),dtf1);
 			
 				if(datetimeparsedForAnswerDislikes.plusHours(24).isAfter(LocalDateTime.now()))
 				{
 					// setting to 1 if the user has clapped in last 24 hours
 					AnswerisClappedIn24HourAndAidWrapperClass newobj=new AnswerisClappedIn24HourAndAidWrapperClass();
 					newobj.setAid(listOfdatetimeAndAidOfAnswerByThisUser2.get(i).getAid());
 					newobj.setIsClappedIn24Hour(1);
 					IsAlreadyDislikedList.add(newobj);
  				}
 	    }
 	    
 	    
 	    
		 //getting count of clap of answer
		List<AnswerClapCountAndAidWrapper> clapcountofanswer=clapsAnswerRepo.getClapCountAidByAid(listofaid);

 		String newJsonData1 = mapper.writeValueAsString(clapcountofanswer);
 		//System.out.println("newJsondata1 "+newJsonData1);
 		model.addAttribute("clapofanswer",newJsonData1);
 		
 		
		 //getting count of dislikes of answer
		List<AnswerDislikesCountAndAidWrapper> dislikecountofanswer=dislikesAnswersRepo.getDislikesCountAidByAid(listofaid);

		String dislikeStringCount = mapper.writeValueAsString(dislikecountofanswer);
		//System.out.println("newJsondata1 "+newJsonData1);
		model.addAttribute("dislikesofanswer",dislikeStringCount);
 		
 		
 		String alreadyClappedByUserOrNotData=mapper.writeValueAsString(IsAlreadyClappedList);
 		//System.out.println("alreadyClappedByUserData "+alreadyClappedByUserOrNotData );
 		model.addAttribute("alreadyClappedByUserData",alreadyClappedByUserOrNotData);
 		
 		
		String alreadyDislikedByUserOrNotData=mapper.writeValueAsString(IsAlreadyDislikedList);
 		//System.out.println("alreadyClappedByUserData "+alreadyClappedByUserOrNotData );
 		model.addAttribute("alreadyDislikedByUserData",alreadyDislikedByUserOrNotData);
 		
		return "discuss-id";
	}
}
