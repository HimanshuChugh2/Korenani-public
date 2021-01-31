package jp.korenani.korenani.entities;

 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;

@Entity
public class Question {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String title;
	private String type;
	private String tags;
	
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String data;
	private String date;
	private String userprofilename;
	
	
	
 

	public void setUserprofilename(String userprofilename) {
		this.userprofilename = userprofilename;
	}

 
	public String getUserprofilename() {
		return userprofilename;
	}



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Question(int id, String title, String type, String data , String tags, String date, String userprofilename) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
		this.data = data;
		this.tags= tags;
		this.date=date;
		this.userprofilename=userprofilename;
	}

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
