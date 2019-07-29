package TrainMe.TrainMe.logic.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.RecommendationService;
import TrainMe.TrainMe.logic.entity.RecommendationEntity;

@Service
public class JPARecommendation implements RecommendationService{
	private IFireBase firebaseService;

	@Autowired
	public void init(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
	}

	@Override
	public RecommendationEntity getRecommendation(long userNumber) throws Exception {
			
			return this.firebaseService.getRecommendations(userNumber);
		
	}

}