package TrainMe.TrainMe.logic.entity;

public class CourseEntity {
	private String courseName;
    private String trainerName;
    private long trainerNumber;
    private String description;
    private String trainerId;
    private String time;
    private String currentNumOfUsersInCourse;
    private String courseLocation;
    private String maxNumOfUsersInCourse;
    private String courseId;
    private String date;
    private long courseNum;
    
	public CourseEntity() {
		super();
		this.currentNumOfUsersInCourse="0";
	}

	public CourseEntity(String courseName, String trainerName, String trainerId, String time,
			String currentNumOfUsersInCourse, String courseLocation, String maxNumOfUsersInCourse, String courseId,
			String date,String description, long courseNum,long trainerNumber) {
		super();
		this.courseName = courseName;
		this.trainerName = trainerName;
		this.trainerId = trainerId;
		this.time = time;
		this.currentNumOfUsersInCourse = currentNumOfUsersInCourse;
		this.courseLocation = courseLocation;
		this.maxNumOfUsersInCourse = maxNumOfUsersInCourse;
		this.courseId = courseId;
		this.date = date;
		this.courseNum=courseNum;
		this.description=description;
		this.trainerNumber=trainerNumber;
	}

	public String getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(String trainerId) {
		this.trainerId = trainerId;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCurrentNumOfUsersInCourse() {
		return currentNumOfUsersInCourse;
	}

	public void setCurrentNumOfUsersInCourse(String currentNumOfUsersInCourse) {
		this.currentNumOfUsersInCourse = currentNumOfUsersInCourse;
	}

	public String getCourseLocation() {
		return courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}

	
	public String getMaxNumOfUsersInCourse() {
		return maxNumOfUsersInCourse;
	}

	public void setMaxNumOfUsersInCourse(String maxNumOfUsersInCourse) {
		this.maxNumOfUsersInCourse = maxNumOfUsersInCourse;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(long courseNum) {
		this.courseNum = courseNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTrainerNumber() {
		return trainerNumber;
	}

	public void setTrainerNumber(long trainerNumber) {
		this.trainerNumber = trainerNumber;
	}
	
}
