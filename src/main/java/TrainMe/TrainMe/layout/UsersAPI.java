package TrainMe.TrainMe.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.UsersTO;
import TrainMe.TrainMe.logic.UsersService;
import TrainMe.TrainMe.logic.entity.UsersEntity;

@RestController
public class UsersAPI {
	private UsersService userService;
	
	@Autowired
	public void setUserService(UsersService userService)
	{
		this.userService=userService;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/trainme/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins="*")
	public UsersTO addTrainer(@RequestBody UsersTO userTO)
	{
		UsersEntity userEntity=userTO.toEntity();
		this.userService.saveUser(userEntity);
		return new UsersTO(userEntity);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/trainme/removeUser/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins="*")
	public void removeTrainerById(@PathVariable("id") String id)
	{
		this.userService.deleteByUserId(id);
	}
}
