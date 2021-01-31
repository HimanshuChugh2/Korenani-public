package jp.korenani.korenani.entities;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;

@Entity
public class Answer {
	@Id	
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String data;
	private int qid;
	private String date;
	private String userprofilename;

	
	public Answer() {
		
		super();
		// TODO Auto-generated constructor stub
	}
	public Answer(int id, String data, String date, String userprofilename,int qid) {
		super();
		this.id = id;
		this.data = data;
		this.date = date;
		this.qid=qid;
		this.userprofilename = userprofilename;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUserprofilename() {
		return userprofilename;
	}
	public void setUserprofilename(String userprofilename) {
		this.userprofilename = userprofilename;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	
	
	
}
