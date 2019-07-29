package TrainMe.TrainMe.layout.TO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import TrainMe.TrainMe.logic.entity.GraphsEntity;

public class GraphsTO {
	private String courseName;
    private String date;
    private int calories;
    private int HR_avg;
    private List<Integer> hrList;
    
	public GraphsTO(String courseName, String date, int calories, int hR_avg, List<Integer> hrList) {
		super();
		this.courseName = courseName;
		this.date = date;
		this.calories = calories;
		HR_avg = hR_avg;
		this.hrList = hrList;
	}
	public GraphsTO(GraphsEntity graphsEntity) {
		this.courseName=graphsEntity.getCourseName();
		this.date=graphsEntity.getDate();
		this.calories=graphsEntity.getCalories();
		this.HR_avg=graphsEntity.getHR_avg();
		this.hrList=graphsEntity.getHrList();
	}
	
	public GraphsTO() {
		super();
		this.hrList=new ArrayList<>();
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
    public GraphsEntity toEntity()
    {
    	GraphsEntity graphsEntity=new GraphsEntity();
    	graphsEntity.setCourseName(this.courseName);
    	graphsEntity.setDate(this.date);
    	graphsEntity.setCalories(this.calories);
    	graphsEntity.setHR_avg(this.HR_avg);
    	graphsEntity.setHrList(this.hrList);
    	return graphsEntity;

    }
    
    
    
}
