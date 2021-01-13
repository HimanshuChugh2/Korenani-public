package jp.korenani.korenani.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DislikesOfAnswer {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int dislikes;
	private int aid;
	private String userprofilename;
 	private String datetime;
	 
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	public String getUserprofilename() {
		return userprofilename;
	}
	public void setUserprofilename(String userprofilename) {
		this.userprofilename = userprofilename;
	}
	public DislikesOfAnswer(int id, int dislikes, int aid, String userprofilename, String datetime) {
		super();
		this.id = id;
		this.dislikes = dislikes;
		this.aid = aid;
		this.userprofilename = userprofilename;
		this.datetime = datetime;
	}
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
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
 
 
	public DislikesOfAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	public int getClaps() {
		return dislikes;
	}
	public void setClaps(int dislikes) {
		this.dislikes = dislikes;
	}
	 

	
	
}
