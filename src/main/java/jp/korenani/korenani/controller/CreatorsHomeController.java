package jp.korenani.korenani.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.Lob;
import javax.xml.ws.Response;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

 import ch.qos.logback.core.joran.conditional.ElseAction;
import jp.korenani.korenani.entities.CcIdWrapper;
import jp.korenani.korenani.entities.CreateContent;
import jp.korenani.korenani.entities.DraftContent;
import jp.korenani.korenani.repository.CreateContentRepository;

@Controller
@RequestMapping("/creators")
public class CreatorsHomeController {

	@Autowired
	CreateContentRepository CreateContentRepo;
	
	@GetMapping
	public String getMappingStringCreators()
	{
	return "creators-home";	
	}
	
	
	@GetMapping("authored-by-me")
	public String byMe(Model model)
	{
		// i have to use findByUsername because using self made function was throwing error, when i tried to get only topic and description, because entity was expecting more fields but i was retrieving only topic and description
		
		//here i only want description topic and id, but i have to get all the data, so it may cause performance issue, so i will create a table using other fields and a table using only the data field, so that data is not getting retrieved which is not useful
		String uString="himanshuchugh2@gmail.com";
		List<CreateContent> contentData=	CreateContentRepo.findByUsername(uString);
		System.out.println(contentData.get(0));
		model.addAttribute("contentData", contentData);
		return "authored-by-me";
	}
	
	
	@GetMapping("/authored-by-me/edit/{id}")
	public String openDraftWithId(@PathVariable("id") int id, Model model)
	{
		CreateContent createContent = CreateContentRepo.getDataByIdQuery(id);
		System.out.println("in get of made-by-me/{id}");
 		model.addAttribute("createContent", createContent);
		return "creating-mode";
	}
	
	
	//this function is returning data but in frontend i have to make it readOnly (disable quill)
	@GetMapping("/authored-by-me/{id}")
	public String ShowCOntentReadOnly(@PathVariable("id") int id, Model model)
	{
		CreateContent createContent = CreateContentRepo.getDataByIdQuery(id);
		System.out.println("in get of made-by-me/{id}");
 		model.addAttribute("createContent", createContent);
		return "creating-mode";
	}
	
	@GetMapping("/creating")
	public String creatingGet(Model model) throws JsonProcessingException
	{
		//Optional<CreateContent> weOptional= CreateContentRepo.findById(67);
		//System.out.println("W E O P T I O N A L  "+ weOptional+"  W E O P T I O N A L ");
		
		/*
		 * ObjectMapper oMapper=new ObjectMapper(); String
		 * json=oMapper.writeValueAsString(weOptional);
		 * model.addAttribute("convertedJson", json);
		 */
		
		
 		
		CreateContent createContent=new CreateContent();
		model.addAttribute("createContent",createContent);
		System.out.println("in get of creating");
		return "creating-mode";
	}
 
	
	@PostMapping(path = "/creating",consumes = "application/json")
 	@ResponseStatus(HttpStatus.OK)
  	public ResponseEntity<String> creatorsPost(@RequestBody CcIdWrapper ccIdWrapper, Model model) throws IOException
	{
		// one user can not have more than one article with the same name
		System.out.println("Cid IS "+ccIdWrapper.getCid());

		if(ccIdWrapper.getCid()!=0)
		{
			CreateContentRepo.updateContentById(ccIdWrapper.getCid(), ccIdWrapper.getCreateContent().getData(), ccIdWrapper.getCreateContent().getDescription(), ccIdWrapper.getCreateContent().getKeywords(), ccIdWrapper.getCreateContent().getLevel(), ccIdWrapper.getCreateContent().getTopic(), ccIdWrapper.getCreateContent().getUsername());
			System.out.println("saved by using cid");
		}
		else if(CreateContentRepo.findById((ccIdWrapper.getCreateContent().getId())).isPresent())
		{
			System.out.println("Same article already exists in CC, Article will be updated with id: "+ ccIdWrapper.getCreateContent().getId());
			CreateContentRepo.updateContentById(ccIdWrapper.getCreateContent().getId(), ccIdWrapper.getCreateContent().getData(), ccIdWrapper.getCreateContent().getDescription(), ccIdWrapper.getCreateContent().getKeywords(), ccIdWrapper.getCreateContent().getLevel(), ccIdWrapper.getCreateContent().getTopic(), ccIdWrapper.getCreateContent().getUsername());
			
		}
		else if(ccIdWrapper.getCreateContent().getData().length()>=9999999)
		{
			System.out.println(" Bigger ");
 			return new ResponseEntity<>("result successful result",
					   HttpStatus.OK);	
		}
		else 
		{
			//createContent.setData(data);
			System.out.println(" Not Bigger ");
			CreateContentRepo.save(ccIdWrapper.getCreateContent());		
		}
		 
		return new ResponseEntity<>("result successful result", 
				   HttpStatus.OK);	
		}
}