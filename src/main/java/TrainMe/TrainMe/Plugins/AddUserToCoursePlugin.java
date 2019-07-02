package TrainMe.TrainMe.Plugins;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class AddUserToCoursePlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;

	@Autowired
	public AddUserToCoursePlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public Object invokeAction(ActivityEntity activityEntity)  {
		UserInCourse addUserToCourse = new UserInCourse();
		try {
			addUserToCourse = this.jackson.readValue(activityEntity.getAttributesJson(), UserInCourse.class);
			this.firebaseService.addUserToCourse(addUserToCourse.getCourseEntity().getCourseId(), addUserToCourse.getUser());
			return addUserToCourse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
