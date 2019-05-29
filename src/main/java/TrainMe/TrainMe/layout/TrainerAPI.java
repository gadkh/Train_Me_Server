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

import TrainMe.TrainMe.layout.TO.TrainerTO;
import TrainMe.TrainMe.logic.TrainerService;
import TrainMe.TrainMe.logic.entity.TrainerEntity;
@CrossOrigin(origins = "*")
@RestController
public class TrainerAPI {
	private TrainerService trainerService;
	
	@Autowired
	public void setTrainerService(TrainerService trainerService)
	{
		this.trainerService=trainerService;
	}
	

	@RequestMapping(method = RequestMethod.POST, path = "/trainme/trainers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@CrossOrigin(origins="*")
	public TrainerTO addTrainer(@RequestBody TrainerTO trainerTO)
	{
		TrainerEntity trainerEntity=trainerTO.toEntity();
		this.trainerService.saveTrainer(trainerEntity);
		return new TrainerTO(trainerEntity);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/trainme/removeTrainer", consumes = MediaType.APPLICATION_JSON_VALUE)
	//@CrossOrigin(origins="*")
	public void removeTrainer(@RequestBody TrainerTO trainerTO)
	{
		
		TrainerEntity trainerToRemove=trainerTO.toEntity();
		this.trainerService.deleteByTrainer(trainerToRemove);
	}
	
	//@CrossOrigin(origins="*")
	@RequestMapping(method = RequestMethod.DELETE, path = "/trainme/removeTrainer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeTrainerById(@PathVariable("id") String id)
	{
		System.err.println("Cors");
		this.trainerService.deleteByTrainerId(id);
	}
	
	//@CrossOrigin(origins="*")
	@RequestMapping(method = RequestMethod.GET, path = "/trainme/getAllTrainers", produces = MediaType.APPLICATION_JSON_VALUE)
	public TrainerTO[] getAllTrainers() {
		return trainerService.getAllTrainers()
				.stream()
				.map(TrainerTO::new)
				.collect(Collectors.toList())
				.toArray(new TrainerTO[0]);
	}
}
