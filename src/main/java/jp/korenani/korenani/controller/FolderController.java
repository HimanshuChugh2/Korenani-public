package jp.korenani.korenani.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.Soundbank;

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
import org.springframework.web.bind.annotation.ResponseStatus;

import jp.korenani.korenani.entities.CreateContent;
import jp.korenani.korenani.entities.CreatedFolder;
import jp.korenani.korenani.entities.FolderMoveHelper;
import jp.korenani.korenani.repository.CreateContentRepository;
import jp.korenani.korenani.repository.FolderRepository;
import scala.inline;

@Controller
@RequestMapping("/creators")
public class FolderController {

	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		  if (principal instanceof UserDetails) return ((UserDetails)
		  principal).getUsername();
		 
		return principal.toString(); 
	}
	
	@Autowired
	FolderRepository folderRepo;
	
	
	@Autowired
	CreateContentRepository createContentRepo;
	
	@GetMapping("create-folder")
	public String createFolder()
	{ 
		return "create-folder";
	}
	
	@GetMapping("folders")
	public String allFoldersGet(Model model , @AuthenticationPrincipal OAuth2User principal)
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
		
		
		
		
		
		
	    List<Object[]> createdFolders=  folderRepo.getAllFoldersByUsername(email);
	    //System.out.println("---createdFolders.get(0))--- "+createdFolders.get(0));
	    model.addAttribute("folderdata",createdFolders);
	    
	    if(createdFolders.size()==0)
	    {
			model.addAttribute("folderMessage","You have not created any folder. Click here to create a folder !");

	    //System.out.println("F O L D E R N A M E I S "+x[0]);
	    }	
	    else {
  		    model.addAttribute("folderMessage", createdFolders.size()+" folders found.");

		}
	    // will show all the articles by user in alphabetical and old-to-new order and from there user will have option of moving articles to a folder
	    List<Object[]> contentList= createContentRepo.getTopicAndIdByUsername(email);
		model.addAttribute("allContentByUser",contentList);
	    return "folders";
	}
	
	
	@PostMapping(path = "/folders",consumes = "application/json")
 	@ResponseStatus(HttpStatus.OK)
  public ResponseEntity<String> MoveFolder(@RequestBody FolderMoveHelper folderMoveHelper, Model model, @AuthenticationPrincipal OAuth2User principal) throws IOException
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
		 
		 
		
		
		//idsarray contains ids of article which should be moved to foldername Folder
		//first delete if the content exists in any folder
		// then insert in the selected folder
		
		//deleting
		folderRepo.deleteExistingContentByArray(folderMoveHelper.getIdsArray());
		
		for(int i=0;i<folderMoveHelper.getIdsArray().length;i++)
		{
 			CreatedFolder createdFolder= new CreatedFolder();
			createdFolder.setContentid(folderMoveHelper.getIdsArray()[i]);
			createdFolder.setFoldername(folderMoveHelper.getFoldername());
			createdFolder.setUsername(email);
			folderRepo.save(createdFolder);
		
			
			
			// i was using this before but it started throwing error "Field 'id' doesn't have a default value at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException", so i tried to save it using "save" which i thought would work the same.
			/*
			 * folderRepo.insertNewContentidInFolder(folderMoveHelper.getIdsArray()[i],
			 * folderMoveHelper.getFoldername(), email);
			 */			
		}
		
		return new ResponseEntity<>("result successful result", 
				   HttpStatus.OK);
  	}
	
 	@PostMapping(path = "/create-folder",consumes = "application/json")
 	@ResponseStatus(HttpStatus.OK)
  	public ResponseEntity<String> create_folder_post(@RequestBody CreatedFolder createdFolder, Model model, @AuthenticationPrincipal OAuth2User principal) throws IOException
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
 		createdFolder.setUsername(email);
 		folderRepo.save(createdFolder);
 		return new ResponseEntity<>("result successful result", 
				   HttpStatus.OK);	
  	}
 	
 	@GetMapping("/folders/{foldername}")
 	@ResponseStatus(HttpStatus.OK)
 	public  String showFoldersById(@PathVariable("foldername") String foldername, Model model, @AuthenticationPrincipal OAuth2User principal)
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
 		
 		
 		// get the content which is put in this "foldername" and show it to user 
 		List<Integer> contentidslist= folderRepo.getContentIdByFoldername(foldername,email);
 		
  		
 		//will show the content registered with contentidslist  to user
 		List<Object[]> dataList= createContentRepo.getTopicById(contentidslist);
 		
 		
 		if(dataList.isEmpty()) 
 		{
  			model.addAttribute("emptyFolder","Folder is empty");
 		}
 		else 
 		{
  	 		model.addAttribute("dataReceivedFromTopicById",dataList);
 			return "folder-by-name";
		}
 		
// 		
// 		List<Integer> createContentIds= createContentRepo.getDataByUsername("himanshuchugh2@gmail.com");
// 		System.out.println(createContentIds);
// 		
 		
 		
 		
// 		List<CreatedFolder> createdFolders= folderRepo.getCCdataSubQuery(createContentIds, foldername);
// 		System.out.println(createdFolders.get(1).getContentid());
 		
 		// i am getting the data i want now i just have to implement in thymelaef and show data associated withcreatedFolders.get(index).getContentid()
 		// i have all the contentid which is stored in a folder, how to show it is the problem, maybe i need to revise @OneTOMany or related annotation
 		
  		return "folder-by-name";
	}
 	
 	
 	
  
 	
}


// all the posts that are currently in a folder
// all the posts that are not in any folder


// all posts - posts that have a contentid in folder table= posts that does not have any folder
// select * from korenani.created_folder kn where kn.foldername in ('JLPT N3' , 'JLPT N5');

//select 

































