package TrainMe.TrainMe.logic.JPA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.GraphsService;
import TrainMe.TrainMe.logic.entity.GraphsEntity;

@Service
public class JPAGraphsService implements GraphsService{
	
	private IFireBase firebaseService;

	@Autowired
	public void init(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
	}
	
	@Override
	public List<GraphsEntity> getAllGraphs(String id) {
		return this.firebaseService.getAllGraphs(id);
	}

}
