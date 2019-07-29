package TrainMe.TrainMe.layout.TO;

import java.util.ArrayList;
import java.util.List;

import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.CourseRateEntity;

public class CourseRateTO {
	private List<Integer>rates;
	private float avg;
	private String courseName;
	private String trainerName;
	private String numOfUsers;
	private String maxNumOfUsers;
	private long courseNum;

	public CourseRateTO(List<Integer> rates, float avg, String courseName, String trainerName, String numOfUsers,
			String maxNumOfUsers, long courseNum) {
		super();
		this.rates = rates;
		this.avg = avg;
		this.courseName = courseName;
		this.trainerName = trainerName;
		this.numOfUsers = numOfUsers;
		this.maxNumOfUsers = maxNumOfUsers;
		this.courseNum = courseNum;
	}

	public CourseRateTO() {
		super();
		this.rates=new ArrayList<>();
	}
	
	public CourseRateTO(CourseRateEntity courseRateEntity) {
		this.rates=courseRateEntity.getRates();
		this.avg=courseRateEntity.getAvg();
		this.courseName=courseRateEntity.getCourseName();
		this.trainerName=courseRateEntity.getTrainerName();
		this.numOfUsers=courseRateEntity.getNumOfUsers();
		this.maxNumOfUsers=courseRateEntity.getMaxNumOfUsers();
		this.courseNum=courseRateEntity.getCourseNum();
	}

	public List<Integer> getRates() {
		return rates;
	}

	public void setRates(List<Integer> rates) {
		this.rates = rates;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
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

	public String getNumOfUsers() {
		return numOfUsers;
	}

	public void setNumOfUsers(String numOfUsers) {
		this.numOfUsers = numOfUsers;
	}

	public String getMaxNumOfUsers() {
		return maxNumOfUsers;
	}

	public void setMaxNumOfUsers(String maxNumOfUsers) {
		this.maxNumOfUsers = maxNumOfUsers;
	}
	
	public long getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(long courseNum) {
		this.courseNum = courseNum;
	}

	public CourseRateEntity toEntity()
	{
		CourseRateEntity courseRateEntity=new CourseRateEntity();
		courseRateEntity.setAvg(this.avg);
		courseRateEntity.setCourseName(this.courseName);
		courseRateEntity.setMaxNumOfUsers(this.maxNumOfUsers);
		courseRateEntity.setNumOfUsers(this.numOfUsers);
		courseRateEntity.setRates(this.rates);
		courseRateEntity.setTrainerName(this.trainerName);
		courseRateEntity.setCourseNum(this.courseNum);
		return courseRateEntity;
	}
	
}
