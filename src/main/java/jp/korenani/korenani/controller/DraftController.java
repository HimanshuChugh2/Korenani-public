package jp.korenani.korenani.controller;

import java.io.IOException;
import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus; 
import jp.korenani.korenani.entities.DraftContent;
import jp.korenani.korenani.repository.CreateContentRepository;
import jp.korenani.korenani.repository.DraftContentRepository;
import jp.korenani.korenani.repository.JPASignUpRepository;

@Controller
@RequestMapping("/creators")
public class DraftController {
	@Autowired
	DraftContentRepository DraftContentRepo;
	
	@Autowired
	CreateContentRepository CreateContentRepo;
	
	
	@Autowired
	JPASignUpRepository jpaSignUpRepository;
	
	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		  if (principal instanceof UserDetails) return ((UserDetails)
		  principal).getUsername();
		 
		return principal.toString(); 
	}
	
	@GetMapping("/drafts")
	public String getDrafts(Model model,@AuthenticationPrincipal OAuth2User principal){
		
		String email="";
 	   
		if(principal!=null)
		{
 		email=principal.getAttribute("email").toString();
		}
		else if(getLoggedInUserName()!=null)
		{
 			email=getLoggedInUserName();
  		}
		
		Optional<String> UserProfileName=jpaSignUpRepository.getUserProfileNameByUsername(email);
		
		
 		List<DraftContent> draftContent=DraftContentRepo.findByUsername(email);
		model.addAttribute("draftData",draftContent);
  		return "drafts";
	}
	
	
	@PostMapping(path = "/save_as_draft",consumes = "application/json")
 	@ResponseStatus(HttpStatus.OK)
  	public ResponseEntity<String> creatorsPost(@RequestBody DraftContent draftContent, Model model, @AuthenticationPrincipal OAuth2User principal) throws IOException
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
		
		
		
		draftContent.setUsername(email);

		
		
		
 		
		// checks if the draft already exists or not

		 if(DraftContentRepo.findById((draftContent.getId())).isPresent())
		{
 			DraftContentRepo.updateDraft(draftContent.getId(), draftContent.getData(), draftContent.getDescription(), draftContent.getKeywords(), draftContent.getLevel(), draftContent.getTopic(), draftContent.getUsername(),draftContent.getCid());
 		}
		// the user has clicked on "edit" button from /authored-by-me
		 // it will check if id exist in cc table, if the id exist that means that the content is already published and we need to save cid in dc table  || nested if statement checks if the draft with same id is already saved, so the user can not save one type of draft multiple times, if nested if statement is not there it will save every time when the content is saved in cc, so nested statement prevents that, and lets user save draft only once, it will check if cid already exists, if it does then it will not save again, will just show the error
		 else if(CreateContentRepo.findById(draftContent.getId()).isPresent())
				{
			 
			 if(DraftContentRepo.findByCid(draftContent.getId()).isPresent()==false)
			 	{
				 draftContent.setCid(draftContent.getId());
 					DraftContentRepo.save(draftContent);
			 	}
			 	else 
			 		{
			 		// coming in this scope means that, user is editing from http://localhost:8080/creators/authored-by-me/edit/{id} but a draft with the same id already exists, so logically the user should edit it from drafts. that is why I need to ask user to go to drafts and edit from there.
			 		model.addAttribute("DraftAlreadyExists","Draft already exists, please go to drafts and edit from there");
 					return new ResponseEntity<>("result successful result", HttpStatus.NOT_ACCEPTABLE);				 
			 		}
				}
	 
		// THIS IS A FRESH DRAFT MEANS THAT NEVER CREATED BEFORE
		else 
		{
 			DraftContentRepo.save(draftContent);
		} 
		return new ResponseEntity<>("result successful result", HttpStatus.OK);
		}
	
	@GetMapping("/drafts/{id}")
	public String openDraftWithId(@PathVariable("id") int id, Model model)
	{
		DraftContent draftContent = DraftContentRepo.getDataByIdQuery(id);
	 
		model.addAttribute("cid", draftContent.getCid());
		model.addAttribute("createContent", draftContent);
		return "creating-mode";
	}
	

}
