package TrainMe.TrainMe.Plugins;

import TrainMe.TrainMe.logic.entity.UsersEntity;

public class WaitingList {
	private String courseId;
	private UsersEntity user;

	public WaitingList() {
		super();
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) {
		this.user = user;
	}


}
