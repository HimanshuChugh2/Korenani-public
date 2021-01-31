package jp.korenani.korenani.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.korenani.korenani.entities.Question;
import jp.korenani.korenani.entities.SignUpUserDetails;
import jp.korenani.korenani.repository.AnswerRepository;
import jp.korenani.korenani.repository.JPASignUpRepository;
import jp.korenani.korenani.repository.QuestionRepository;
import jp.korenani.korenani.wrappers.AnswerPagintationContainingQuestionTitleWrapper;
import jp.korenani.korenani.wrappers.AnswerPagintationWrapperInterface;
import jp.korenani.korenani.wrappers.QuestionPagintationWrapperInterface;
import jp.korenani.korenani.wrappers.UserprofileNameAndPageNumberWrapper;
import scala.reflect.internal.Trees.New;

@Controller
@RequestMapping("{username}")
public class UserProfileController {

	@Autowired
	JPASignUpRepository jpaSignUpRepository ;
	
	
	@Autowired
	QuestionRepository questionRepository;
	
	
	@Autowired
	AnswerRepository answerRepository;
	
	
	@GetMapping("/edit")
	public String showProfileForEdit(@PathVariable("username") String username,Model model)
	{
		SignUpUserDetails signUpUserDetails= jpaSignUpRepository.getUserByUsername(username);
		model.addAttribute("user",signUpUserDetails);
 

		//System.out.println(XQuestions.get(0).getTitle());
		
 		
 		// getting qid,id,date from answer table
		List<AnswerPagintationWrapperInterface> twoAnswers= answerRepository.getTwoAnswers(username);
		
		
		//getting data from question table based on the above answer
		//AnswerPagintationContainingQuestionTitleWrapper 
 
 
		return "user-homepage-editable";
	}
	
	
	
	@GetMapping
	public String ShowUserHomePage(@PathVariable("username") String username, Model model) throws JsonProcessingException
	{
		
		
		SignUpUserDetails signUpUserDetails= jpaSignUpRepository.getUserByUsername(username);
		model.addAttribute("user",signUpUserDetails);
	 
		
		
		
		//pagintation
		 
//		ObjectMapper mapper = new ObjectMapper();
//		String vString=mapper.writeValueAsString(questionRepository.getXQuestions(username, 5));
 		model.addAttribute("TwoQuestions",questionRepository.getTwoQuestions(username));
 
		//System.out.println(XQuestions.get(0).getTitle());
		
 		
		
		// getting qid,id,date from answer table
		//List<AnswerPagintationWrapperInterface> twoAnswers= answerRepository.getTwoAnswers(username);
		
		
		//getting data from question table based on the above answer
		//AnswerPagintationContainingQuestionTitleWrapper 
 
 		
		model.addAttribute("twoAnswers");
		model.addAttribute("userprofilename",username);	
		return "user-homepage";
	}
	
	
	
	@PostMapping("/view-more-questions")
	public ResponseEntity<String> viewXQuestion(@RequestBody UserprofileNameAndPageNumberWrapper userprofileNameAndPageNumberWrapper) throws JsonProcessingException
	{
		 
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		
		
		return new ResponseEntity<>(mapper.writeValueAsString(questionRepository.getXQuestions(userprofileNameAndPageNumberWrapper.getUserprofilename(), userprofileNameAndPageNumberWrapper.getPageNumber())) , HttpStatus.OK);				 

	}
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/update-user-info")
	public ResponseEntity<String> updateUserInfo(@RequestBody SignUpUserDetails signUpUserDetails, Model model)
	{
		// if user has entered a userprofilename which is already existing, then do not let them enter that userprofilename
		
		 
		
		
		// checking if quill was updated or not, if it was updated then i will update all the fields, if not then only two fields will be updated
		if(signUpUserDetails.getUserhomepagedata()!=null)
		{
			jpaSignUpRepository.updateAllUserDetailsByEmail(signUpUserDetails.getUserProfileName(), signUpUserDetails.getName(),signUpUserDetails.getUserhomepagedata(), signUpUserDetails.getUsername());
 		}
		else {
		  
			//updating user details
 
		jpaSignUpRepository.updateUserDetailsByEmail(signUpUserDetails.getUserProfileName(), signUpUserDetails.getName(), signUpUserDetails.getUsername());
		}
			
		 return new ResponseEntity<>("result successful result", 
				   HttpStatus.OK);	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
