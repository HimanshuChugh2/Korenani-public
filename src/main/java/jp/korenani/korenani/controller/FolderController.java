package jp.korenani.korenani.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.Soundbank;

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
	public String allFoldersGet(Model model)
	{
	    List<Object[]> createdFolders=  folderRepo.getAllFoldersByUsername("himanshuchugh2@gmail.com");
	    
	    model.addAttribute("folderdata",createdFolders);
	    Object[] x=createdFolders.get(0); 
	    //System.out.println("F O L D E R N A M E I S "+x[0]);
	    
	    // will show all the articles by user in alphabetical and old-to-new order and from there user will have option of moving articles to a folder
	    List<Object[]> contentList= createContentRepo.getTopicAndIdByUsername("himanshuchugh2@gmail.com");
		model.addAttribute("allContentByUser",contentList);
	    return "folders";
	}
	
	
	@PostMapping(path = "/folders",consumes = "application/json")
 	@ResponseStatus(HttpStatus.OK)
  public ResponseEntity<String> MoveFolder(@RequestBody FolderMoveHelper folderMoveHelper, Model model) throws IOException
  	{
		 
		System.out.println("fn "+folderMoveHelper.getFoldername());
		System.out.println(folderMoveHelper.getIdsArray());
		
		
		//idsarray contains ids of article which should be moved to foldername Folder
		//first delete if the content exists in any folder
		// then insert in the selected folder
		
		//deleting
		folderRepo.deleteExistingContentByArray(folderMoveHelper.getIdsArray());
		
		for(int i=0;i<folderMoveHelper.getIdsArray().length;i++)
		{
			System.out.println("inserting "+folderMoveHelper.getIdsArray()[i]);
			folderRepo.insertNewContentidInFolder(folderMoveHelper.getIdsArray()[i], folderMoveHelper.getFoldername(), "himanshuchugh2@gmail.com");
			
		}
		
		return new ResponseEntity<>("result successful result", 
				   HttpStatus.OK);
  	}
	
 	@PostMapping(path = "/create-folder",consumes = "application/json")
 	@ResponseStatus(HttpStatus.OK)
  	public ResponseEntity<String> create_folder_post(@RequestBody CreatedFolder createdFolder, Model model) throws IOException
  	{
 		
 		createdFolder.setUsername("himanshuchugh2@gmail.com");
 		folderRepo.save(createdFolder);
 		return new ResponseEntity<>("result successful result", 
				   HttpStatus.OK);	
  	}
 	
 	@GetMapping("/folders/{foldername}")
 	@ResponseStatus(HttpStatus.OK)
 	public  String showFoldersById(@PathVariable("foldername") String foldername, Model model)
 	{
 		
 		// get the content which is put in this "foldername" and show it to user 
 		List<Integer> contentidslist= folderRepo.getContentIdByFoldername(foldername,"himanshuchugh2@gmail.com");
 		
 		System.out.println(" C O N T E N T I D "+contentidslist);
 		
 		//will show the content registered with contentidslist  to user
 		List<Object[]> dataList= createContentRepo.getTopicById(contentidslist);
 		
 		
 		if(dataList.isEmpty())
 		{
 			System.out.println(" E M P T Y ");
 			model.addAttribute("emptyFolder","Folder is empty");
 		}
 		else 
 		{
 			System.out.println("D A T A L I S T I S "+ dataList.get(0)[0]);
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
 		
 		System.out.println("path variable foldername is "+ foldername);
 		return "folder-by-name";
	}
 	
 	
 	
  
 	
}


// all the posts that are currently in a folder
// all the posts that are not in any folder


// all posts - posts that have a contentid in folder table= posts that does not have any folder
// select * from korenani.created_folder kn where kn.foldername in ('JLPT N3' , 'JLPT N5');

//select 

































