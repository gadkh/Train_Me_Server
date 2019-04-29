package TrainMe.TrainMe.Plugins;

import java.util.ArrayList;
import java.util.List;

import TrainMe.TrainMe.logic.entity.UsersEntity;

public class UserInCourse {

	private String courseId;
	private UsersEntity user;
	private int hrAVG;
	private List<Integer> hrlist;

	public UserInCourse() {
		super();
		this.hrlist = new ArrayList<Integer>();
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
}
