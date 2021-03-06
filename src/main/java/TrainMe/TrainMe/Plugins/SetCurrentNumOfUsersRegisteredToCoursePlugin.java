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
		UserInCourse userInCourse=new UserInCourse();
		try {
			userInCourse = this.jackson.readValue(activityEntity.getAttributesJson(), UserInCourse.class);
			int currentNumOfUserInCourse=Integer.parseInt(userInCourse.getCourseEntity().getCurrentNumOfUsersInCourse());
			this.firebaseService.setCurrentNumOfUsersRegisteredToCourse(userInCourse.getCourseEntity().getCourseId(),currentNumOfUserInCourse);
			return userInCourse;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);		
			}
	}
}
