package TrainMe.TrainMe.layout.TO;

import java.util.Map;

import TrainMe.TrainMe.logic.entity.ActivityEntity;

public class ActivitiTO {
	
	private String type;
	private Map<String,Object>moreAttributes;
	public ActivitiTO() {
		super();
	}
	public ActivitiTO(String type, Map<String, Object> moreAttributes) {
		super();
		this.type = type;
		this.moreAttributes = moreAttributes;
	}
	
	public ActivitiTO(ActivityEntity activityEntity)
	{
		this.type=activityEntity.getType();
		this.moreAttributes=activityEntity.getMoreAttributes();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getMoreAttributes() {
		return moreAttributes;
	}
	public void setMoreAttributes(Map<String, Object> moreAttributes) {
		this.moreAttributes = moreAttributes;
	}
	
	public ActivityEntity toEntity()
	{
		ActivityEntity activityEntity=new ActivityEntity();
		activityEntity.setType(this.type);
		activityEntity.setMoreAttributes(this.moreAttributes);
		return activityEntity;
	}
	
}
