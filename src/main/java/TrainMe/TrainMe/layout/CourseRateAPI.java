package TrainMe.TrainMe.layout;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.CourseRateTO;
import TrainMe.TrainMe.logic.CourseRateService;
import TrainMe.TrainMe.logic.entity.CourseRateEntity;

@RestController
public class CourseRateAPI {

private CourseRateService courseRateService;
	
	@Autowired
	public void setUserService(CourseRateService courseRateService)
	{
		this.courseRateService=courseRateService;
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(method = RequestMethod.GET, path = "/trainme/getCourseRates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CourseRateTO getCourseRates(@PathVariable("id") String id) {	
		CourseRateEntity courseRateEntity=courseRateService.getCourseRates(id);
		CourseRateTO coursRateTO=new CourseRateTO(courseRateEntity);
		return coursRateTO;
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(method = RequestMethod.GET, path = "/trainme/getAllCourseRates", produces = MediaType.APPLICATION_JSON_VALUE)
	public CourseRateTO[] getAllRatesCourse() {		
		return courseRateService.getAllRatesCourse()
				.stream()
				.map(CourseRateTO::new)
				.collect(Collectors.toList())
				.toArray(new CourseRateTO[0]);
	}
}
