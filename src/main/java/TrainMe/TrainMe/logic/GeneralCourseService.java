package TrainMe.TrainMe.logic;

import java.util.List;

import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;


public interface GeneralCourseService {
	public GeneralCourseEntity saveGeneralCourse(GeneralCourseEntity generalCourseEntity);
	public void deleteByGeneralCourseName(String generalCourseName);
	public List<GeneralCourseEntity> getAllGeneralCourses();

}
