package jp.korenani.korenani.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CreatedFolder {

	private String foldername;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private int contentid;
	private String username;
	public String getFoldername() {
		return foldername;
	}

	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContentid() {
		return contentid;
	}

	public void setContentid(int contentid) {
		this.contentid = contentid;
	}

	public CreatedFolder(String foldername, int id, int contentid,String username) {
		super();
		this.foldername = foldername;
		this.id = id;
		this.contentid = contentid;
		this.username=username;
	}

	public CreatedFolder(String foldername) {
		super();
		this.foldername = foldername;
	}

	public CreatedFolder() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
