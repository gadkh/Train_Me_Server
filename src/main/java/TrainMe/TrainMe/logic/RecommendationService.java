package TrainMe.TrainMe.logic;

import TrainMe.TrainMe.logic.entity.RecommendationEntity;

public interface RecommendationService {
	public RecommendationEntity getRecommendation(long userNumber) throws Exception;
}