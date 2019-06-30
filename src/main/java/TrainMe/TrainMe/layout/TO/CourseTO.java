package TrainMe.TrainMe.layout.TO;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import TrainMe.TrainMe.logic.entity.CourseEntity;

public class CourseTO {
	private String courseName;
    private TrainerTO trainer;
    private String time;
    private String currentNumOfUsersInCourse;
    private String courseLocation;
    private String maxNumOfUsersInCourse;
    private String date;
    private String courseId;
    private long courseNum;
    
	public CourseTO() {
		super();
		this.currentNumOfUsersInCourse="0";
	}

	public CourseTO(CourseEntity courseEntity)
	{
		this.courseName=courseEntity.getCourseName();
		this.trainer=new TrainerTO();
		this.trainer.setName(courseEntity.getTrainerName());
		this.trainer.setId(courseEntity.getTrainerId());
		this.trainer.setTrainerNum(courseEntity.getTrainerNumber());
		this.time=courseEntity.getTime();
		this.currentNumOfUsersInCourse=courseEntity.getCurrentNumOfUsersInCourse();
		this.courseLocation=courseEntity.getCourseLocation();
		this.maxNumOfUsersInCourse=courseEntity.getMaxNumOfUsersInCourse();
		this.date=courseEntity.getDate();
		this.courseId=courseEntity.getCourseId();
		this.courseNum=courseEntity.getCourseNum();
	}

	public CourseTO(String courseName, TrainerTO trainer, String time, String currentNumOfUsersInCourse,
			String courseLocation, String maxNumOfUsersInCourse, String date,String courseId, long courseNum) {
		super();
		this.courseName = courseName;
		this.trainer = trainer;
		this.time = time;
		this.currentNumOfUsersInCourse = currentNumOfUsersInCourse;
		this.courseLocation = courseLocation;
		this.maxNumOfUsersInCourse = maxNumOfUsersInCourse;
		this.date = date;
		this.courseId=courseId;
		this.courseNum=courseNum;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public TrainerTO getTrainer() {
		return trainer;
	}

	public void setTrainer(TrainerTO trainer) {
		this.trainer = trainer;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	

	public long getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(long courseNum) {
		this.courseNum = courseNum;
	}

	public CourseEntity toEntity()
	{
		CourseEntity courseEntity=new CourseEntity();
		courseEntity.setCourseName(this.courseName);
		courseEntity.setTrainerName(this.trainer.getName());
		courseEntity.setTrainerId(this.trainer.getId());
		courseEntity.setTrainerNumber(this.trainer.getTrainerNum());
		courseEntity.setTime(this.time);
		courseEntity.setCurrentNumOfUsersInCourse(this.currentNumOfUsersInCourse);
		courseEntity.setCourseLocation(this.courseLocation);
		courseEntity.setMaxNumOfUsersInCourse(this.maxNumOfUsersInCourse);
		courseEntity.setCourseNum(this.courseNum);
		courseEntity.setDate(this.date);
		
		//Create Course ID
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat currentTimeToString = new SimpleDateFormat("HH:mm:ss:SSS");
        this.courseId = currentTimeToString.format(calendar.getTime()).toString();
		courseEntity.setCourseId(courseId);
		return courseEntity;
	}
    
}
