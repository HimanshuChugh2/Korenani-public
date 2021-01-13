package jp.korenani.korenani.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

 
 

@Entity
public class CreateContent {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String username;
	private String level;
	private String topic;
	
	@Lob
    private String data;
	private String description;
	private String keywords;
	 
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public CreateContent(int id, String username, String level, String topic, String data, String description,
			String keywords) {
		super();
		this.id = id;
		this.username = username;
		this.level = level;
		this.topic = topic;
		this.data = data;
		this.description = description;
		this.keywords = keywords;
	}
	public CreateContent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CreateContent(int id, String username, String level, String topic, String data) {
		super();
		this.id = id;
		this.username = username;
		this.level = level;
		this.topic = topic;
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	

}
