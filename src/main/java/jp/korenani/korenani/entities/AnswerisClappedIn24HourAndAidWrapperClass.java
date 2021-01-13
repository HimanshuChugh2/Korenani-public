package jp.korenani.korenani.entities;

public class AnswerisClappedIn24HourAndAidWrapperClass {

	private int isClappedIn24Hours;
	private int aid;
	
	public AnswerisClappedIn24HourAndAidWrapperClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AnswerisClappedIn24HourAndAidWrapperClass(int isClappedIn24Hours, int aid) {
		super();
		this.isClappedIn24Hours = isClappedIn24Hours;
		this.aid = aid;
	}

	public int getIsClappedIn24Hours() {
		return isClappedIn24Hours;
	}
	public void setIsClappedIn24Hour(int isClappedIn24Hours) {
		this.isClappedIn24Hours = isClappedIn24Hours;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	
	
	
}

