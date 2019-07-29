package TrainMe.TrainMe.logic.JPA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.CourseRateService;
import TrainMe.TrainMe.logic.entity.CourseRateEntity;

@Service
public class JPACourseRateService implements CourseRateService{

	private IFireBase firebaseService;

	@Autowired
	public void init(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
	}
	@Override
	public CourseRateEntity getCourseRates(String courseId) {
		// TODO Auto-generated method stub
		return this.firebaseService.getCourseRates(courseId);
	}
	@Override
	public List<CourseRateEntity> getAllRatesCourse() {
		// TODO Auto-generated method stub
		return this.firebaseService.getAllRatesCourse();
	}
}
