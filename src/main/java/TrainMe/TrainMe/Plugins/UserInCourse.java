package TrainMe.TrainMe.Plugins;

import java.util.ArrayList;
import java.util.List;

import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

public class UserInCourse {

	private CourseEntity courseEntity;
	private UsersEntity user;
	private int hrAVG;
	private List<Integer> hrlist;
	private int rate;
	private boolean isDatePassed;
	private boolean isOnWaitingList;
	private boolean isRegisterdToCourse;
	private boolean isFull;
	
	public UserInCourse() {
		super();
		this.hrlist = new ArrayList<Integer>();
	}

	public CourseEntity getCourseEntity() {
		return courseEntity;
	}

	public void setCourseEntity(CourseEntity courseEntity) {
		this.courseEntity = courseEntity;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) {
		this.user = user;
	}

	public int getHrAVG() {
		return hrAVG;
	}

	public void setHrAVG(int hrAVG) {
		this.hrAVG = hrAVG;
	}

	public List<Integer> getHrlist() {
		return hrlist;
	}

	public void setHrlist(List<Integer> hrlist) {
		this.hrlist = hrlist;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean isDatePassed() {
		return isDatePassed;
	}

	public void setDatePassed(boolean isDatePassed) {
		this.isDatePassed = isDatePassed;
	}

	public boolean isOnWaitingList() {
		return isOnWaitingList;
	}

	public void setOnWaitingList(boolean isOnWaitingList) {
		this.isOnWaitingList = isOnWaitingList;
	}

	public boolean isRegisterdToCourse() {
		return isRegisterdToCourse;
	}

	public void setRegisterdToCourse(boolean isRegisterdToCourse) {
		this.isRegisterdToCourse = isRegisterdToCourse;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
	
}
