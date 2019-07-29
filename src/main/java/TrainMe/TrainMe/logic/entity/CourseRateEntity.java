package TrainMe.TrainMe.logic.entity;

import java.util.ArrayList;
import java.util.List;

public class CourseRateEntity {
	private List<Integer>rates;
	private float avg;
	private String courseName;
	private String trainerName;
	private String numOfUsers;
	private String maxNumOfUsers;
	private long courseNum;
	
	
	

	public CourseRateEntity(List<Integer> rates, float avg, String courseName, String trainerName, String numOfUsers,
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

	public CourseRateEntity() {
		super();
		this.rates=new ArrayList<>();
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
	
}
