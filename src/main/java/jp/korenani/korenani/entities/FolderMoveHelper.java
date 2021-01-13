package jp.korenani.korenani.entities;

public class FolderMoveHelper {

	private Integer[] idsArray;
	private String foldername;
	public Integer[] getIdsArray() {
		return idsArray;
	}
	public void setIdsArray(Integer[] idsArray) {
		this.idsArray = idsArray;
	}
	public String getFoldername() {
		return foldername;
	}
	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}
	public FolderMoveHelper(Integer[] idsArray, String foldername) {
		super();
		this.idsArray = idsArray;
		this.foldername = foldername;
	}
	public FolderMoveHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
