package TrainMe.TrainMe.logic.entity;

public class GeneralCourseEntity {
	private String name;
	private String description;
	private long generalCourseEntityNum;

	public GeneralCourseEntity()
	{
		super();
	}
	
	public GeneralCourseEntity(String name,String description,long generalCourseEntityNum)
	{
		this.name=name;
		this.description=description;
		this.generalCourseEntityNum=generalCourseEntityNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getGeneralCourseEntityNum() {
		return generalCourseEntityNum;
	}

	public void setGeneralCourseEntityNum(long generalCourseEntityNum) {
		this.generalCourseEntityNum = generalCourseEntityNum;
	}
	
}
