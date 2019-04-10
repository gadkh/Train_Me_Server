package TrainMe.TrainMe.Plugins;

public class IsOnWaitingList {
	private String userId;
	private String courseId;
	private boolean onWaitingList;

	public IsOnWaitingList() {
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

	public boolean isOnWaitingList() {
		return onWaitingList;
	}

	public void setOnWaitingList(boolean onWaitingList) {
		this.onWaitingList = onWaitingList;
	}

}
