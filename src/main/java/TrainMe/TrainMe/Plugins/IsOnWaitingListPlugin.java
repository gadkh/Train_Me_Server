package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class IsOnWaitingListPlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public IsOnWaitingListPlugin(IFireBase firebaseService) {
		this.firebaseService=firebaseService;
		this.jackson = new ObjectMapper();
	}
	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		UserInCourse userInCourse=new UserInCourse();
		try {
			userInCourse=this.jackson.readValue(activityEntity.getAttributesJson(), UserInCourse.class);
			userInCourse.setOnWaitingList((firebaseService.isOnWaitingList(userInCourse.getCourseEntity().getCourseId(), userInCourse.getUser().getUserId())));
			return userInCourse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
