package TrainMe.TrainMe.logic.entity;

public class RecommendationEntity {

	private String courseName;
	private String trainerName;
	
	public RecommendationEntity() {
		super();
	}

	public RecommendationEntity(String courseName, String trainerName) {
		super();
		this.courseName = courseName;
		this.trainerName = trainerName;
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
	
	
	
}
