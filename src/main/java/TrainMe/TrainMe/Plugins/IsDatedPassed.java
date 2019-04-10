package TrainMe.TrainMe.Plugins;

public class IsDatedPassed {

	private String courseId;
	private boolean isDatedPassed;

	public IsDatedPassed() {
		super();
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public boolean isDatedPassed() {
		return isDatedPassed;
	}

	public void setDatedPassed(boolean isDatedPassed) {
		this.isDatedPassed = isDatedPassed;
	}

}
