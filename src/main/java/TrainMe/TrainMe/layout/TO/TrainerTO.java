package TrainMe.TrainMe.layout.TO;

import TrainMe.TrainMe.logic.entity.TrainerEntity;

public class TrainerTO {
	private String name;
	private String id;
	
	public TrainerTO()
	{
		super();
	}
	public TrainerTO(String name,String id)
	{
		this.name=name;
		this.id=id;
	}
	public TrainerTO(TrainerEntity trainerEntity)
	{
		this.name=trainerEntity.getName();
		this.id=trainerEntity.getId();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public TrainerEntity toEntity()
	{
		TrainerEntity trainerEntity=new TrainerEntity();
		trainerEntity.setName(this.name);
		trainerEntity.setId(this.id);
		return trainerEntity;
	}
}
