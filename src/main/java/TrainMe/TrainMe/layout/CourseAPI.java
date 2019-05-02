package TrainMe.TrainMe.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.CourseTO;
import TrainMe.TrainMe.logic.CourseService;
import TrainMe.TrainMe.logic.entity.CourseEntity;

@RestController
public class CourseAPI {
	private CourseService courseService;

	@Autowired
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/trainme/course", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "*")
	public CourseTO addCourse(@RequestBody CourseTO courseTo) {
		CourseEntity courseEntity = courseTo.toEntity();
		this.courseService.saveCourse(courseEntity);
		return new CourseTO(courseEntity);
	}
}
