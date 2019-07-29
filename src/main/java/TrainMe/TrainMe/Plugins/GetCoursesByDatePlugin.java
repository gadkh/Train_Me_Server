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
		try {
			System.err.println("30");
			GetCoursesByDate coursesByDate=this.jackson.readValue(activityEntity.getAttributesJson(), GetCoursesByDate.class);
			System.err.println("31");
			System.err.println(coursesByDate.getDate());
			coursesByDate.setCoursesList(this.firebaseService.getCoursesByDate(coursesByDate.getDate()));
			return coursesByDate;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
