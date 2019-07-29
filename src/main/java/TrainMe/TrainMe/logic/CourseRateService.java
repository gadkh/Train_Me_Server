package TrainMe.TrainMe.logic;

import java.util.List;

import TrainMe.TrainMe.logic.entity.CourseRateEntity;

public interface CourseRateService {
	public CourseRateEntity getCourseRates(String courseId);
	public List<CourseRateEntity> getAllRatesCourse();

}
