package TrainMe.TrainMe.logic.entity;

public class UsersEntity {
	private String fullName;
	private String height;
	private String gender;
	private String userId;
	private String weigh;
	
	public UsersEntity() {
		super();
	}

	public UsersEntity(String fullName, String height, String gender, String userId, String weigh) {
		super();
		this.fullName = fullName;
		this.height = height;
		this.gender = gender;
		this.userId = userId;
		this.weigh = weigh;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWeigh() {
		return weigh;
	}

	public void setWeigh(String weigh) {
		this.weigh = weigh;
	}
	
	

}
