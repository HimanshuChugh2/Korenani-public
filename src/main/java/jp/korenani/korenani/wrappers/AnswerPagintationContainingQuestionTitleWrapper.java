package jp.korenani.korenani.wrappers;


public class AnswerPagintationContainingQuestionTitleWrapper {

	private int id;
	private int qid;
	private String title;
	private String date;
	private String userprofilename;
	public AnswerPagintationContainingQuestionTitleWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AnswerPagintationContainingQuestionTitleWrapper(int id, int qid, String title, String date,
			String userprofilename) {
		super();
		this.id = id;
		this.qid = qid;
		this.title = title;
		this.date = date;
		this.userprofilename = userprofilename;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
}
