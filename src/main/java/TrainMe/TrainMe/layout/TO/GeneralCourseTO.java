package TrainMe.TrainMe.layout.TO;

import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;

public class GeneralCourseTO {
	private String name;
	private String description;
	private long generalCourseEntityNum;
	
	public GeneralCourseTO()
	{
		super();
	}
	public GeneralCourseTO(String name,String description, long generalCourseEntityNum)
	{
		this.name=name;
		this.description=description;
		this.generalCourseEntityNum= generalCourseEntityNum;
	}
	public GeneralCourseTO(GeneralCourseEntity generalCourseEntity)
	{
		this.name=generalCourseEntity.getName();
		this.description=generalCourseEntity.getDescription();
		this.generalCourseEntityNum=generalCourseEntity.getGeneralCourseEntityNum();
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
	public GeneralCourseEntity toEntity()
	{
		GeneralCourseEntity generalCourseEntity=new GeneralCourseEntity();
		generalCourseEntity.setName(this.name);
		generalCourseEntity.setDescription(this.description);
		generalCourseEntity.setGeneralCourseEntityNum(this.generalCourseEntityNum);
		return generalCourseEntity;
	}
}

