package TrainMe.TrainMe.logic.entity;

import java.util.Map;

import javax.persistence.Lob;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActivityEntity {
	
	private String type;
	private Map<String,Object>moreAttributes;
	
	public ActivityEntity() {
		super();
	}

	public ActivityEntity(String type, Map<String, Object> moreAttributes) {
		super();
		this.type = type;
		this.moreAttributes = moreAttributes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Transient
	public Map<String, Object> getMoreAttributes() {
		return moreAttributes;
	}

	public void setMoreAttributes(Map<String, Object> moreAttributes) {
		this.moreAttributes = moreAttributes;
	}
	

	@Lob
	@JsonIgnore
	public String getAttributesJson() {
		try {
			return new ObjectMapper().writeValueAsString(this.moreAttributes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@JsonIgnore
	public void setAttributesJson(String json) {
		try {
			this.moreAttributes = new ObjectMapper().readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
