package TrainMe.TrainMe.logic.entity;

import java.util.List;

public class GraphsEntity {
	private String courseName;
    private String date;
    private int calories;
    private int HR_avg;
    private List<Integer> hrList;

	public GraphsEntity(String courseName, String date, int calories, int hR_avg, List<Integer> hrList) {
		super();
		this.courseName = courseName;
		this.date = date;
		this.calories = calories;
		HR_avg = hR_avg;
		this.hrList = hrList;
	}

	public GraphsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getHR_avg() {
		return HR_avg;
	}

	public void setHR_avg(int hR_avg) {
		HR_avg = hR_avg;
	}

	public List<Integer> getHrList() {
		return hrList;
	}

	public void setHrList(List<Integer> hrList) {
		this.hrList = hrList;
	}
}
