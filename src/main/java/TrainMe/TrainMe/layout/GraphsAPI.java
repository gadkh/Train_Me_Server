package TrainMe.TrainMe.layout;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.GraphsTO;
import TrainMe.TrainMe.layout.TO.UsersTO;
import TrainMe.TrainMe.logic.GraphsService;
import TrainMe.TrainMe.logic.UsersService;

@RestController
public class GraphsAPI {

	private GraphsService graphsService;
	
	@Autowired
	public void setUserService(GraphsService graphsService)
	{
		this.graphsService=graphsService;
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(method = RequestMethod.GET, path = "/trainme/getAllGraphs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GraphsTO[] getAllGraphs(@PathVariable("id") String id) {		
		return graphsService.getAllGraphs(id)
				.stream()
				.map(GraphsTO::new)
				.collect(Collectors.toList())
				.toArray(new GraphsTO[0]);
	}
}
