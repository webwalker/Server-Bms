package bms.core.model;

/**
 * @author xu.jian
 * 
 */
public class MyResponse {
	private boolean success;
	private String message;
	private Object ext;

	public MyResponse(boolean success) {
		this.success = success;
	}

	public MyResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public MyResponse(boolean success, String message, Object ext) {
		this.success = success;
		this.message = message;
		this.ext = ext;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getExt() {
		return ext;
	}

	public void setExt(Object ext) {
		this.ext = ext;
	}

}
