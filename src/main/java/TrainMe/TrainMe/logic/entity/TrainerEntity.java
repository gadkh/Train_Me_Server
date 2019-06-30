package TrainMe.TrainMe.logic.entity;

public class TrainerEntity {
	private String name;
	private String id;
	private long trainerNum;
	
	public TrainerEntity()
	{
		super();
	}
	public TrainerEntity(String name,String id, long trainerNum)
	{
		this.name=name;
		this.id=id;
		this.trainerNum=trainerNum;
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
	
	
}
