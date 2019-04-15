package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;

@Component
public class UpdateGeneralCoursePlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public UpdateGeneralCoursePlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		GeneralCourseEntity generalCourse=new GeneralCourseEntity();
		try {
			generalCourse = this.jackson.readValue(activityEntity.getAttributesJson(), GeneralCourseEntity.class);
			this.firebaseService.updateGeneralCourse(generalCourse);
			return generalCourse;
		} catch (IOException e) {
			throw new RuntimeException(e);		
			}
	}
}
