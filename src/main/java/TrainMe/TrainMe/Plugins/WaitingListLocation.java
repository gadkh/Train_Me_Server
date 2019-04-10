package TrainMe.TrainMe.Plugins;

import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

public class WaitingListLocation {
	private int location;
	private UsersEntity user;
	private String courseId;

	public WaitingListLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) {
		this.user = user;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}
