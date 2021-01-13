package jp.korenani.korenani.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus; 
import jp.korenani.korenani.entities.DraftContent;
import jp.korenani.korenani.repository.CreateContentRepository;
import jp.korenani.korenani.repository.DraftContentRepository;

@Controller
@RequestMapping("/creators")
public class DraftController {
	@Autowired
	DraftContentRepository DraftContentRepo;
	
	@Autowired
	CreateContentRepository CreateContentRepo;
	
	
	@GetMapping("/drafts")
	public String getDrafts(Model model)
	{ 	String uString="himanshuchugh2@gmail.com";
		List<DraftContent> draftContent=DraftContentRepo.findByUsername(uString);
		System.out.println("retrived");
		model.addAttribute("draftData",draftContent);
		return "drafts";
	}
	
	
	@PostMapping(path = "/save_as_draft",consumes = "application/json")
 	@ResponseStatus(HttpStatus.OK)
  	public ResponseEntity<String> creatorsPost(@RequestBody DraftContent draftContent, Model model) throws IOException
	{
		
		
		
 		
		// checks if the draft already exists or not

		 if(DraftContentRepo.findById((draftContent.getId())).isPresent())
		{
			System.out.println("he will be updated with id: "+ draftContent.getId());
			DraftContentRepo.updateDraft(draftContent.getId(), draftContent.getData(), draftContent.getDescription(), draftContent.getKeywords(), draftContent.getLevel(), draftContent.getTopic(), draftContent.getUsername(),draftContent.getCid());
			System.out.println("U P D A T E D");	
		}
		// the user has clicked on "edit" button from /authored-by-me
		 // it will check if id exist in cc table, if the id exist that means that the content is already published and we need to save cid in dc table  || nested if statement checks if the draft with same id is already saved, so the user can not save one type of draft multiple times, if nested if statement is not there it will save every time when the content is saved in cc, so nested statement prevents that, and lets user save draft only once, it will check if cid already exists, if it does then it will not save again, will just show the error
		 else if(CreateContentRepo.findById(draftContent.getId()).isPresent())
				{
			 
			 if(DraftContentRepo.findByCid(draftContent.getId()).isPresent()==false)
			 	{
				 draftContent.setCid(draftContent.getId());
					System.out.println("the user has clicked on \"edit\" button from /authored-by-me"+ CreateContentRepo.getTopicByUsername("himanshuchugh2@gmail.com", draftContent.getTopic())+"    " + draftContent.getId());
					DraftContentRepo.save(draftContent);
			 	}
			 	else 
			 		{
			 		model.addAttribute("DraftAlreadyExists","Draft already exists, please go to drafts and edit from there");
			 		System.out.println("Draft already exists, please go to drafts and edit from there");
					return new ResponseEntity<>("result successful result", HttpStatus.OK);				 
			 		}
				}
	 
		// THIS IS A FRESH DRAFT MEANS THAT NEVER CREATED BEFORE
		else 
		{
			System.out.println("he will be Saved");
			DraftContentRepo.save(draftContent);
		} 
		return new ResponseEntity<>("result successful result", HttpStatus.OK);
		}
	
	@GetMapping("/drafts/{id}")
	public String openDraftWithId(@PathVariable("id") int id, Model model)
	{
		DraftContent draftContent = DraftContentRepo.getDataByIdQuery(id);
		System.out.println("in get of drafts{id}");
		System.out.println(draftContent.getDescription());
		System.out.println(draftContent.getCid());
		model.addAttribute("cid", draftContent.getCid());
		model.addAttribute("createContent", draftContent);
		return "creating-mode";
	}
	

}
