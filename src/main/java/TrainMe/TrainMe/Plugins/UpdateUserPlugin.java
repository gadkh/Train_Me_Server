package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

@Component
public class UpdateUserPlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public UpdateUserPlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		UsersEntity userEntity=new UsersEntity();
		try {
			userEntity = this.jackson.readValue(activityEntity.getAttributesJson(), UsersEntity.class);
			this.firebaseService.updateUser(userEntity);
			return userEntity;
		} catch (IOException e) {
			throw new RuntimeException(e);		
			}
	}
}
