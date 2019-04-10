package TrainMe.TrainMe.logic.entity;

public class TrainerEntity {
	private String name;
	private String id;
	
	public TrainerEntity()
	{
		super();
	}
	public TrainerEntity(String name,String id)
	{
		this.name=name;
		this.id=id;
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
	
	
}
