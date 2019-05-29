package TrainMe.TrainMe.logic;

import java.util.List;

import TrainMe.TrainMe.logic.entity.TrainerEntity;

public interface TrainerService {
	public TrainerEntity saveTrainer(TrainerEntity newTrainer);
	public void deleteByTrainer(TrainerEntity trainetToDelete);
	public void deleteByTrainerId(String trainertId);
	public List<TrainerEntity> getAllTrainers();
}
