package TrainMe.TrainMe.Plugins;

import java.util.ArrayList;
import java.util.List;

import TrainMe.TrainMe.logic.entity.CourseEntity;

public class GetCoursesByDate {
	private String date;
	private List<CourseEntity>coursesList;
	
	public GetCoursesByDate() {
		super();
		this.coursesList=new ArrayList<>();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<CourseEntity> getCoursesList() {
		return coursesList;
	}

	public void setCoursesList(List<CourseEntity> coursesList) {
		this.coursesList = coursesList;
	}
	
	
	
}
