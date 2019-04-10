package TrainMe.TrainMe.Plugins;

public class CheckCourseIsFull {
	
	private boolean full;

	public CheckCourseIsFull() {
		super();
	}

	public CheckCourseIsFull(boolean full) {
		super();
		this.full = full;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}
	
	
}
