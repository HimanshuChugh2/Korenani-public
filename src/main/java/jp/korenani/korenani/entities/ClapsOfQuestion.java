package jp.korenani.korenani.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 

@Entity
public class ClapsOfQuestion {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int claps;
	private int qid;
	private String userprofilename;
 	private String datetime;
	 
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
 
 
	public ClapsOfQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	public ClapsOfQuestion(int id, int claps, int qid, String userprofilename, String datetime) {
		super();
		this.id = id;
		this.claps = claps;
		this.qid = qid;
		this.userprofilename = userprofilename;
		this.datetime = datetime;
	}
	public String getUserprofilename() {
		return userprofilename;
	}
	public void setUserprofilename(String userprofilename) {
		this.userprofilename = userprofilename;
	}
	public int getClaps() {
		return claps;
	}
	public void setClaps(int claps) {
		this.claps = claps;
	}
	 
	
	
}
//created username for future use, if we want to know who all people have upvoted a particular question/answer
