package TrainMe.TrainMe.layout.TO;

import TrainMe.TrainMe.logic.entity.UsersEntity;

public class UsersTO {
	private String fullName;
	private String height;
	private String gender;
	private String userId;
	private String weigh;

	public UsersTO() {
		super();
	}

	public UsersTO(String fullName, String height, String gender, String userId, String weigh) {
		super();
		this.fullName = fullName;
		this.height = height;
		this.gender = gender;
		this.userId = userId;
		this.weigh = weigh;
	}

	public UsersTO(UsersEntity userEntity) {
		this.fullName=userEntity.getFullName();
		this.gender=userEntity.getGender();
		this.height=userEntity.getHeight();
		this.userId=userEntity.getUserId();
		this.weigh=userEntity.getWeigh();
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
	
	public UsersEntity toEntity()
	{
		UsersEntity userEntity=new UsersEntity();
		userEntity.setFullName(this.fullName);
		userEntity.setGender(this.gender);
		userEntity.setHeight(this.height);
		userEntity.setUserId(this.userId);
		userEntity.setWeigh(this.weigh);
		return userEntity;
	}
}
