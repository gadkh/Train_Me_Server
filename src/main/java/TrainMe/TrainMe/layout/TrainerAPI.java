package TrainMe.TrainMe.layout;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.TrainerTO;
import TrainMe.TrainMe.logic.TrainerService;
import TrainMe.TrainMe.logic.entity.TrainerEntity;

@RestController
public class TrainerAPI {
	private TrainerService trainerService;
	
	@Autowired
	public void setTrainerService(TrainerService trainerService)
	{
		this.trainerService=trainerService;
	}
	
//	@RequestMapping(method = RequestMethod.GET, path = "/stam", produces = MediaType.APPLICATION_JSON_VALUE)
//	@CrossOrigin(origins="*")
//	public String stam()
//	{
//		return "Hi Golan";
//	}
//	
//	
//	@RequestMapping(method = RequestMethod.POST, path = "/trainme/temp", consumes = MediaType.APPLICATION_JSON_VALUE)
//	@CrossOrigin(origins="*")
//	public void Temp(@RequestBody String name)
//	{
//		System.err.println(name);
//	}
//	
	
	@RequestMapping(method = RequestMethod.POST, path = "/trainme/trainers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins="*")
	public TrainerTO addTrainer(@RequestBody TrainerTO trainerTO)
	{
		TrainerEntity trainerEntity=trainerTO.toEntity();
		this.trainerService.saveTrainer(trainerEntity);
		return new TrainerTO(trainerEntity);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/trainme/removeTrainer", consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins="*")
	public void removeTrainer(@RequestBody TrainerTO trainerTO)
	{
		
		TrainerEntity trainerToRemove=trainerTO.toEntity();
		this.trainerService.deleteByTrainer(trainerToRemove);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/trainme/removeTrainer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins="*")
	public void removeTrainerById(@PathVariable("id") String id)
	{
		this.trainerService.deleteByTrainerId(id);
	}
}
