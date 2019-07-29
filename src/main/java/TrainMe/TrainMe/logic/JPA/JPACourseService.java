package TrainMe.TrainMe.logic.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.CourseService;
import TrainMe.TrainMe.logic.entity.CourseEntity;

@Service
public class JPACourseService implements CourseService {

	private IFireBase firebaseService;

	@Autowired
	public void init(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
	}

	@Override
	public CourseEntity saveCourse(CourseEntity courseEntity) {
		return this.firebaseService.addCourse(courseEntity);

	}
	
@Override
	public void deleteByCourseId(String courseId) {
		// TODO Auto-generated method stub
	this.firebaseService.deleteByCourse(courseId);	
	}
}
