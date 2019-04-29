package TrainMe.TrainMe.Plugins;

public class CurrentNumOfUsersInCourse {
	private String courseId;
	private int currentNumOfUsers;
	public CurrentNumOfUsersInCourse() {
		super();
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
