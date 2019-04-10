package TrainMe.TrainMe.Plugins;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class GetCoursesByDatePlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public GetCoursesByDatePlugin(IFireBase firebaseService) {
		this.firebaseService=firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		System.err.println("gad2");
		try {
			GetCoursesByDate coursesByDate=this.jackson.readValue(activityEntity.getAttributesJson(), GetCoursesByDate.class);
			coursesByDate.setCoursesList(this.firebaseService.getCoursesByDate(coursesByDate.getDate()));
			return coursesByDate;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
