package TrainMe.TrainMe.Plugins;

public class UserRegisteredToCourse {
	private String courseId;
	private int currentNumOfUsers;
	public UserRegisteredToCourse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public int getCurrentNumOfUsers() {
		return currentNumOfUsers;
	}
	public void setCurrentNumOfUsers(int currentNumOfUsers) {
		this.currentNumOfUsers = currentNumOfUsers;
	}
}
