package jp.korenani.korenani.controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.korenani.korenani.entities.CreateContent;
import jp.korenani.korenani.repository.CreateContentRepository;

@Controller
public class ShowContentController {
	
	@Autowired
	CreateContentRepository repo;
	
	@GetMapping("/showit")
	public String showallString(Model model) throws JsonProcessingException
	{
		CreateContent obj= repo.getDataByIdQuery(220);
		
		
		 
		 //the first image in the editor will be the conver image of your content
		   
		   
		model.addAttribute("articleby",obj.getUsername());
		System.out.println("U S E R N  A M E "+ obj.getUsername());
		model.addAttribute("notconvertedJson", obj.getData());
		return "viewwritten";
		
	}
	

}
