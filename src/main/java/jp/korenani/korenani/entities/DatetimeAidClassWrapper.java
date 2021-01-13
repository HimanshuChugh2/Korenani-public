package jp.korenani.korenani.entities;

public class DatetimeAidClassWrapper {
	
	private String datetime;
	private Integer aid;
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
 
	public DatetimeAidClassWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DatetimeAidClassWrapper(String datetime, Integer aid) {
		super();
		this.datetime = datetime;
		this.aid = aid;
	}
	
	

}
