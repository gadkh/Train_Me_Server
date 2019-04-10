package TrainMe.TrainMe.Plugins;

public class IsUserRegisteredToCourse {
	private String userId;
	private String courseId;
	private boolean registered;
	public IsUserRegisteredToCourse() {
		super();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public boolean isRegistered() {
		return registered;
	}
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
	
	
}
