package jp.korenani.korenani.config;

public class ReCaptchaResponse {

	private boolean success;
	private String challange_t;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getChallange_t() {
		return challange_t;
	}
	public void setChallange_t(String challange_t) {
		this.challange_t = challange_t;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	private String hostname;
}
