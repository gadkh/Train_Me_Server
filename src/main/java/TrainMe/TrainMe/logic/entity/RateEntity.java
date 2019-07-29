package TrainMe.TrainMe.logic.entity;

public class RateEntity {
	private String userId;
	private String courseName;
	private String trainerName;
	private long courseNumber;
	private long userNumber;
	private int rate;

	public RateEntity(String userId, String courseName, String trainerName, long courseNumber,
			long userNumber, int rate) {
		super();
		this.userId = userId;
		this.courseName = courseName;
		this.trainerName = trainerName;
		this.courseNumber = courseNumber;
		this.userNumber = userNumber;
		this.rate = rate;
	}

	public RateEntity() {
		super();
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}

	public long getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(long courseNumber) {
		this.courseNumber = courseNumber;
	}

	public long getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(long userNumber) {
		this.userNumber = userNumber;
	}

	@Override
	public String toString() {
		return "RecommendationEntity [userId=" + userId + ", courseName=" + courseName + ", trainerName=" + trainerName
				+ ", courseNumber=" + courseNumber + ", userNumber=" + userNumber + ", rate=" + rate + "]";
	}
	
}
