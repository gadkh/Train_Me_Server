package TrainMe.TrainMe.Plugins;

public class RateCourse {
	private String courseId;
	private String userId;
	private int rate;
	
	public RateCourse() {
		super();
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	
}
