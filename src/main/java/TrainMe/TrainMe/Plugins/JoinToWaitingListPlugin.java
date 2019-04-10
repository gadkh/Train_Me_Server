package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class JoinToWaitingListPlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;

	@Autowired
	public JoinToWaitingListPlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		JoinToWaitingList joinToWaitingList = new JoinToWaitingList();
		try {
			joinToWaitingList = this.jackson.readValue(activityEntity.getAttributesJson(), JoinToWaitingList.class);
			this.firebaseService.joinToWaitingList(joinToWaitingList.getCourseId(), joinToWaitingList.getUser());
			return joinToWaitingList;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
