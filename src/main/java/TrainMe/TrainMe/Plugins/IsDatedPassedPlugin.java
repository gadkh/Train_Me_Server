package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class IsDatedPassedPlugin implements TrainMePlugins {
	private ObjectMapper jackson;
	private IFireBase firebaseService;

	@Autowired
	public IsDatedPassedPlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		IsDatePassed isDatedPassed = new IsDatePassed();
		try {
			isDatedPassed = this.jackson.readValue(activityEntity.getAttributesJson(), IsDatePassed.class);
			isDatedPassed.setDatedPassed(firebaseService.isDatePassed(isDatedPassed.getCourseId()));
			return isDatedPassed;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
