package TrainMe.TrainMe.layout;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.GeneralCourseTO;
import TrainMe.TrainMe.logic.GeneralCourseService;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;

@RestController
public class GeneralCourseAPI {
	private GeneralCourseService generalCourseService;
	
	@Autowired
	public void setGeneralCourseService(GeneralCourseService generalCourseService)
	{
		this.generalCourseService=generalCourseService;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/trainme/generalCourse", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins="*")
	public GeneralCourseTO addGeneralCourse(@RequestBody GeneralCourseTO generalCourseTo)
	{
		GeneralCourseEntity generalCourseEntity=generalCourseTo.toEntity();
		this.generalCourseService.saveGeneralCourse(generalCourseEntity);
		return new GeneralCourseTO(generalCourseEntity);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/trainme/removeGeneralCourse/{name}")
	@CrossOrigin(origins="*")
	public void removeGeneralCourseByName(@PathVariable("name") String name)
	{
		
		this.generalCourseService.deleteByGeneralCourseName(name);
	}

	@CrossOrigin(origins="*")
	@RequestMapping(method = RequestMethod.GET, path = "/trainme/getAllGeneralCourses", produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralCourseTO[] getAllTrainers() {		
		return generalCourseService.getAllGeneralCourses()
				.stream()
				.map(GeneralCourseTO::new)
				.collect(Collectors.toList())
				.toArray(new GeneralCourseTO[0]);
	}
}
