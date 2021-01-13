package jp.korenani.korenani.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DislikesOfQuestion {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int dislikes;
	private int qid;
	private String userprofilename;
 	private String datetime;
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	 
	public DislikesOfQuestion(int id, int dislikes, int qid, String userprofilename, String datetime) {
		super();
		this.id = id;
		this.dislikes = dislikes;
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
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public DislikesOfQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}
 	
 	
 	
 	
	
	
}