package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class WaitingListLocationPlugin implements TrainMePlugins {
	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public WaitingListLocationPlugin(IFireBase firebaseService) {
		this.firebaseService=firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		WaitingListLocation waitingListLocation=null;
		try {
			waitingListLocation = this.jackson.readValue(activityEntity.getAttributesJson(), WaitingListLocation.class);
			waitingListLocation.setLocation(this.firebaseService.getCurrentNumOfUsersRegisteredToCourse(waitingListLocation.getCourseId()));
			return waitingListLocation;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);		
			}
	}
	
}
