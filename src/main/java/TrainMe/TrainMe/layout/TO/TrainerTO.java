package TrainMe.TrainMe.layout.TO;

import TrainMe.TrainMe.logic.entity.TrainerEntity;

public class TrainerTO {
	private String name;
	private String id;
	private long trainerNum;
	
	public TrainerTO()
	{
		super();
	}
	public TrainerTO(String name,String id, long trainerNum)
	{
		this.name=name;
		this.id=id;
		this.trainerNum=trainerNum;
	}
	public TrainerTO(TrainerEntity trainerEntity)
	{
		this.name=trainerEntity.getName();
		this.id=trainerEntity.getId();
		this.trainerNum=trainerEntity.getTrainerNum();
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
	
	
	public long getTrainerNum() {
		return trainerNum;
	}
	public void setTrainerNum(long trainerNum) {
		this.trainerNum = trainerNum;
	}
	public TrainerEntity toEntity()
	{
		TrainerEntity trainerEntity=new TrainerEntity();
		trainerEntity.setName(this.name);
		trainerEntity.setId(this.id);
		trainerEntity.setTrainerNum(trainerNum);
		return trainerEntity;
	}
}
