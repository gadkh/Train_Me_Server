package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class IsUserRegisteredToCoursePlugin implements TrainMePlugins{
	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public IsUserRegisteredToCoursePlugin(IFireBase firebaseService) {
		this.firebaseService=firebaseService;
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		try {
			IsUserRegisteredToCourse userRegistered=this.jackson.readValue(activityEntity.getAttributesJson(), IsUserRegisteredToCourse.class);
			userRegistered.setRegistered(firebaseService.isUserRegistered(userRegistered.getCourseId(), userRegistered.getUserId()));
			return userRegistered;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
