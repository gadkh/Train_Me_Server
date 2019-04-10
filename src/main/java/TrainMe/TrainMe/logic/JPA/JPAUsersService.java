package TrainMe.TrainMe.logic.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.UsersService;
import TrainMe.TrainMe.logic.entity.UsersEntity;

@Service
public class JPAUsersService implements UsersService{

	private IFireBase firebaseService;

	@Autowired
	public void init(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
	}

	@Override
	public UsersEntity saveUser(UsersEntity userEntity) {
		return this.firebaseService.addUser(userEntity);
	}

	@Override
	public void deleteByUserId(String userId) {
		this.firebaseService.deleteByUserId(userId);
	}
	

}
