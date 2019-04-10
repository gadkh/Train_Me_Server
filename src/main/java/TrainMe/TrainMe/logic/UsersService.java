package TrainMe.TrainMe.logic;

import TrainMe.TrainMe.logic.entity.UsersEntity;

public interface UsersService {
	public UsersEntity saveUser(UsersEntity userEntity);
	public void deleteByUserId(String userId);
}
