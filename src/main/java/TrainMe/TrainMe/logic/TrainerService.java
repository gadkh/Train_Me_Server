package TrainMe.TrainMe.logic;

import TrainMe.TrainMe.logic.entity.TrainerEntity;

public interface TrainerService {
	public TrainerEntity saveTrainer(TrainerEntity newTrainer);
	public void deleteByTrainer(TrainerEntity trainetToDelete);
	public void deleteByTrainerId(String trainertId);
}
