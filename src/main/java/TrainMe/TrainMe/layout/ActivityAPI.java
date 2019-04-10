package TrainMe.TrainMe.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.ActivitiTO;
import TrainMe.TrainMe.logic.ActivityService;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@RestController
public class ActivityAPI {
	ActivityService activityService;
	
	@Autowired
	public void init(ActivityService activityService) {
		this.activityService = activityService;
	}

	@RequestMapping(
			method=RequestMethod.POST,
			path="/trainme/activity",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
		public Object performActivity(@RequestBody ActivitiTO activitiTO)  {
		ActivityEntity activityRetrive=activitiTO.toEntity();
		this.activityService.performActivity(activityRetrive);
		return new ActivitiTO(activityRetrive);
	}
}
