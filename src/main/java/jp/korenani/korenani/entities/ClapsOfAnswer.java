package jp.korenani.korenani.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClapsOfAnswer {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int claps;
	private int aid;
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
	public int getAid() {
		return aid;
	}
	public void setAid(int qid) {
		this.aid = qid;
	}
 
 
	public ClapsOfAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	public ClapsOfAnswer(int id, int claps, int aid, String userprofilename, String datetime) {
		super();
		this.id = id;
		this.claps = claps;
		this.aid = aid;
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
