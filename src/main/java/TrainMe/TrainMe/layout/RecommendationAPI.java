package TrainMe.TrainMe.layout;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.CourseTO;
import TrainMe.TrainMe.layout.TO.TrainerTO;
import TrainMe.TrainMe.layout.TO.UsersTO;
import TrainMe.TrainMe.logic.CourseService;
import TrainMe.TrainMe.logic.RecommendationService;
import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.RecommendationEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

@RestController
public class RecommendationAPI {
	private RecommendationService recommendationService;
	
	@Autowired
	public void setCourseService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.POST, path = "/trainme/recommendation", 
	produces = MediaType.APPLICATION_JSON_VALUE, 
	consumes = MediaType.APPLICATION_JSON_VALUE)
	public RecommendationEntity getRecommendation(@RequestBody UsersTO userTO) throws Exception {
		UsersEntity userEntity=userTO.toEntity();
		return this.recommendationService.getRecommendation(userEntity.getUserNumber());
	}
}