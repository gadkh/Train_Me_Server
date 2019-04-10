package TrainMe.TrainMe.layout.TO;

import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;

public class GeneralCourseTO {
	private String name;
	private String description;
	
	public GeneralCourseTO()
	{
		super();
	}
	public GeneralCourseTO(String name,String description)
	{
		this.name=name;
		this.description=description;
	}
	public GeneralCourseTO(GeneralCourseEntity generalCourseEntity)
	{
		this.name=generalCourseEntity.getName();
		this.description=generalCourseEntity.getDescription();
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
	public GeneralCourseEntity toEntity()
	{
		GeneralCourseEntity generalCourseEntity=new GeneralCourseEntity();
		generalCourseEntity.setName(this.name);
		generalCourseEntity.setDescription(this.description);
		return generalCourseEntity;
	}
}

