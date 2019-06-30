package TrainMe.TrainMe.layout.TO;

import TrainMe.TrainMe.logic.entity.UsersEntity;

public class UsersTO {
	private String fullName;
	private String age;
	private String gender;
	private String userId;
	private String weigh;
	private long userNumber;
	
	public UsersTO() {
		super();
	}

	public UsersTO(String fullName, String age, String gender, String userId, String weigh,long userNumber) {
		super();
		this.fullName = fullName;
		this.age = age;
		this.gender = gender;
		this.userId = userId;
		this.weigh = weigh;
		this.userNumber=userNumber;
	}

	public UsersTO(UsersEntity userEntity) {
		this.fullName=userEntity.getFullName();
		this.gender=userEntity.getGender();
		this.age=userEntity.getAge();
		this.userId=userEntity.getUserId();
		this.weigh=userEntity.getWeigh();
		this.userNumber=userEntity.getUserNumber();
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

	public UsersEntity toEntity()
	{
		UsersEntity userEntity=new UsersEntity();
		userEntity.setFullName(this.fullName);
		userEntity.setGender(this.gender);
		userEntity.setAge(this.age);
		userEntity.setUserId(this.userId);
		userEntity.setWeigh(this.weigh);
		userEntity.setUserNumber(userNumber);
		return userEntity;
	}
}
