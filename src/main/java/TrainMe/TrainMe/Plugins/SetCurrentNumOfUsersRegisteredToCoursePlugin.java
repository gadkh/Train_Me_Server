package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class SetCurrentNumOfUsersRegisteredToCoursePlugin implements TrainMePlugins {
	
	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public SetCurrentNumOfUsersRegisteredToCoursePlugin(IFireBase firebaseService) {
		this.firebaseService=firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		CurrentNumOfUsersInCourse currentNumOfUsersInCourse=new CurrentNumOfUsersInCourse();
		try {
			currentNumOfUsersInCourse = this.jackson.readValue(activityEntity.getAttributesJson(), CurrentNumOfUsersInCourse.class);
			this.firebaseService.setCurrentNumOfUsersRegisteredToCourse(currentNumOfUsersInCourse.getCourseId(),currentNumOfUsersInCourse.getCurrentNumOfUsers());
			return currentNumOfUsersInCourse;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);		
			}
	}

}
