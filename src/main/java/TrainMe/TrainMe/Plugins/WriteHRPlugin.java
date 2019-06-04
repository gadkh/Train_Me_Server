package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class WriteHRPlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public WriteHRPlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		UserInCourse userInCourse=new UserInCourse();
		System.err.println("in plugin");
		try {
			userInCourse = this.jackson.readValue(activityEntity.getAttributesJson(), UserInCourse.class);
			this.firebaseService.writeHr(userInCourse.getCourseEntity().getCourseId(), userInCourse.getUser().getUserId()
					,userInCourse.getHrlist());
			return userInCourse;
		} catch (IOException e) {
			throw new RuntimeException(e);		
			}
	}
}
