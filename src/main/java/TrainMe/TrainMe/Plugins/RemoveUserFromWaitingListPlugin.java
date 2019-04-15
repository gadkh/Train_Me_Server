package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Component
public class RemoveUserFromWaitingListPlugin implements TrainMePlugins {
	private ObjectMapper jackson;
	private IFireBase firebaseService;

	@Autowired
	public RemoveUserFromWaitingListPlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		WaitingList deleteUserFromWaitingList = new WaitingList();
		try {
			deleteUserFromWaitingList = this.jackson.readValue(activityEntity.getAttributesJson(), WaitingList.class);
			this.firebaseService.deleteUserFromWaitingList(deleteUserFromWaitingList.getCourseId(),
					deleteUserFromWaitingList.getUser().getUserId());
			return deleteUserFromWaitingList;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
