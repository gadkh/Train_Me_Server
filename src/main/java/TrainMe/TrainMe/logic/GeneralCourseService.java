package TrainMe.TrainMe.logic;

import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;


public interface GeneralCourseService {
	public GeneralCourseEntity saveGeneralCourse(GeneralCourseEntity generalCourseEntity);
	public void deleteByGeneralCourseName(String generalCourseName);
}
