package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;
import TrainMe.TrainMe.logic.entity.CourseEntity;


@Component
public class CheckCourseIsFullPlugin implements TrainMePlugins{
	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public CheckCourseIsFullPlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		CheckCourseIsFull courseIsFull=new CheckCourseIsFull();
		CourseEntity course;
		try {
			course = this.jackson.readValue(activityEntity.getAttributesJson(), CourseEntity.class);
			courseIsFull.setFull(this.firebaseService.checkCourseIsFull(course.getCourseId()));
			return courseIsFull;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);		
			}
		
		//return courseIsFull;
	}
	
}
