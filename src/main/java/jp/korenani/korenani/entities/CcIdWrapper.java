package jp.korenani.korenani.entities;

public class CcIdWrapper {

	private CreateContent createContent;
	private int cid;
	public CcIdWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CcIdWrapper(CreateContent createContent, int cid) {
		super();
		this.createContent = createContent;
		this.cid = cid;
	}
	public CreateContent getCreateContent() {
		return createContent;
	}
	public void setCreateContent(CreateContent createContent) {
		this.createContent = createContent;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
}
// this class is there because when user clicks on "save" after the article has been saved as a draft, so in that case we need to retrieve the cid and the createContent object, since we cant send two objects in ajax post requests, i have created this class which will accept one CC object and one cid