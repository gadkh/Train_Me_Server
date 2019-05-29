package TrainMe.TrainMe.logic.JPA;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.TrainerService;
import TrainMe.TrainMe.logic.entity.TrainerEntity;


@Service
class JPATrainerService implements TrainerService{
	private IFireBase firebaseService;
	
	@Autowired
	public void init(IFireBase firbaseService){
		this.firebaseService=firbaseService;
	}

	@Override
	public TrainerEntity saveTrainer(TrainerEntity newTrainer) {
		return this.firebaseService.storeTrainer(newTrainer);
	}

	@Override
	public void deleteByTrainer(TrainerEntity trainetToDelete) {
		this.firebaseService.deleteByTrainer(trainetToDelete);
	}

	@Override
	public void deleteByTrainerId(String trainertId) {
		this.firebaseService.deleteByTrainerId(trainertId);
	}
@Override
public List<TrainerEntity> getAllTrainers() {
	return this.firebaseService.getAllTrainers();
}
}


