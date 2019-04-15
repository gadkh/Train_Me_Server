package TrainMe.TrainMe.Plugins;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

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
	public Object invokeAction(ActivityEntity activityEntity) {
		AddUserToCourse addUserToCourse = new AddUserToCourse();
		try {
			addUserToCourse = this.jackson.readValue(activityEntity.getAttributesJson(), AddUserToCourse.class);
			this.firebaseService.addUserToCourse(addUserToCourse.getCourseId(), addUserToCourse.getUser(),
					addUserToCourse.getHrAVG(), addUserToCourse.getHrlist());
			return addUserToCourse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
