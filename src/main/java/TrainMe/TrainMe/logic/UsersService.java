package TrainMe.TrainMe.logic;

import java.util.List;

import TrainMe.TrainMe.logic.entity.UsersEntity;

public interface UsersService {
	public UsersEntity saveUser(UsersEntity userEntity);
	public void deleteByUserId(String userId);
	public List<UsersEntity> getAllUsers();
}
