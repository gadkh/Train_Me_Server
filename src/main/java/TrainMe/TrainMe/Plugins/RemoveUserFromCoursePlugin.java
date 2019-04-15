package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class RemoveUserFromCoursePlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;

	@Autowired
	public RemoveUserFromCoursePlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		RemoveUserFromCourse removeUserFromCourse = new RemoveUserFromCourse();
		try {
			removeUserFromCourse = this.jackson.readValue(activityEntity.getAttributesJson(), RemoveUserFromCourse.class);
			this.firebaseService.deleteUserFromCourse(removeUserFromCourse.getCourseId(),removeUserFromCourse.getUserId());
			return removeUserFromCourse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
