package TrainMe.TrainMe.logic.JPA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.GeneralCourseService;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;

@Service
public class JPAGeneralCourseService implements GeneralCourseService {
	private IFireBase firebaseService;

	@Autowired
	public void init(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
	}

	@Override
	public GeneralCourseEntity saveGeneralCourse(GeneralCourseEntity generalCourseEntity) {
		return this.firebaseService.addGeneralCourse(generalCourseEntity);
	}

	@Override
	public void deleteByGeneralCourseName(String generalCourseName) {
		this.firebaseService.deleteByGeneralCourseName(generalCourseName);
	}

	@Override
	public List<GeneralCourseEntity> getAllGeneralCourses() {
		return this.firebaseService.getAllGeneralCourses();
	}
}
