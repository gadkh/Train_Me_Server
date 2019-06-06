package TrainMe.TrainMe.logic.entity;

public class UsersEntity {
	private String fullName;
	private String age;
	private String gender;
	private String userId;
	private String weigh;
	
	public UsersEntity() {
		super();
	}

	public UsersEntity(String fullName, String age, String gender, String userId, String weigh) {
		super();
		this.fullName = fullName;
		this.age = age;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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
