package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class SetUserRegisteredToCoursePlugin implements TrainMePlugins {
	
	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public SetUserRegisteredToCoursePlugin(IFireBase firebaseService) {
		this.firebaseService=firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		UserRegisteredToCourse userRegisteredToCourse=new UserRegisteredToCourse();
		try {
			userRegisteredToCourse = this.jackson.readValue(activityEntity.getAttributesJson(), UserRegisteredToCourse.class);
			this.firebaseService.setCurrentNumOfUsersRegisteredToCourse(userRegisteredToCourse.getCourseId(),userRegisteredToCourse.getCurrentNumOfUsers());
			return userRegisteredToCourse;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);		
			}
	}

}
