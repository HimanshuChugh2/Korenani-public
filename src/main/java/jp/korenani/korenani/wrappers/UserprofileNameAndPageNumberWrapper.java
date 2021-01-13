package jp.korenani.korenani.wrappers;

public class UserprofileNameAndPageNumberWrapper {
	
	int pageNumber;
	String userprofilename;
	public UserprofileNameAndPageNumberWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserprofileNameAndPageNumberWrapper(int pageNumber, String userprofilename) {
		super();
		this.pageNumber = pageNumber;
		this.userprofilename = userprofilename;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getUserprofilename() {
		return userprofilename;
	}
	public void setUserprofilenameString(String userprofilename) {
		this.userprofilename = userprofilename;
	}
	

}
