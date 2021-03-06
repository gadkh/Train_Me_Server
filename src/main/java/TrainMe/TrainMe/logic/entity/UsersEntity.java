package TrainMe.TrainMe.logic.entity;

public class UsersEntity {
	private String fullName;
	private String age;
	private String gender;
	private String userId;
	private String weigh;
	private String token;
	private long userNumber;
	
	public UsersEntity() {
		super();
	}

	public UsersEntity(String fullName, String age, String gender,String token, String userId, String weigh,long userNumber) {
		super();
		this.fullName = fullName;
		this.age = age;
		this.gender = gender;
		this.token = token;
		this.userId = userId;
		this.weigh = weigh;
		this.userNumber=userNumber;
	}
	
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public long getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(long userNumber) {
		this.userNumber = userNumber;
	}
}
