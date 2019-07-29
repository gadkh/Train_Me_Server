package TrainMe.TrainMe.FireBase.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.CourseRateEntity;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;
import TrainMe.TrainMe.logic.entity.GraphsEntity;
import TrainMe.TrainMe.logic.entity.RateEntity;
import TrainMe.TrainMe.logic.entity.RecommendationEntity;
import TrainMe.TrainMe.logic.entity.TrainerEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

public interface IFireBase {
	/**Trainer Methods**/
	public TrainerEntity storeTrainer(TrainerEntity newTrainer);
	public List<TrainerEntity> getAllTrainers();
	public void deleteByTrainer(TrainerEntity trainetToDelete);
	public void deleteByTrainerId(String trainertId);
	
	/**General Course Methods**/
	public GeneralCourseEntity addGeneralCourse(GeneralCourseEntity generalCourseEntity);
	public GeneralCourseEntity updateGeneralCourse(GeneralCourseEntity generalCourseEntity);
	public void deleteByGeneralCourseName(String generalCourseName);
	public List<GeneralCourseEntity> getAllGeneralCourses();
	
	/**User Methods**/
	public UsersEntity addUser(UsersEntity userEntity);
	public void deleteByUserId(String userId);
	public boolean isUserRegistered(String courseId,String userId);
	public UsersEntity addUserToCourse(String courseId, UsersEntity userEntity) throws IOException;
	public UsersEntity joinToWaitingList(String courseId,UsersEntity userEntity);
	public void deleteUserFromCourse(String courseId, String userId, String courseName);
	public void deleteUserFromWaitingList(String courseId, String userId);
	public UsersEntity updateUser(UsersEntity newUser);
	public UsersEntity getUserById(String id);
	public List<UsersEntity> getAllUsers();
	
	
	/**Course Methods**/
	public CourseEntity addCourse(CourseEntity courseEntity);
	public boolean checkCourseIsFull(String courseId);
	public boolean isOnWaitingList(String courseId,String userId);
	public int positionOnWaitingList(String courseId,String userId);
	public void setCurrentNumOfUsersRegisteredToCourse(String courseId,int newInt);//char sign);
	public int getCurrentNumOfUsersRegisteredToCourse(String courseId);
	public void rateCourse(String courseId,String courseName,String userId, int rate);
	public boolean isDatePassed(String courseId);
	public void writeHr(CourseEntity courseEntite, String userId ,List<Integer>hrList);
	public List<CourseEntity>getCoursesByDate(String date);
	public int calculateCalories(int avgHR,String userId); 
	public CourseEntity getCourseById(String courseId);	
	void deleteWaitingList(String courseId);
	public CourseRateEntity getCourseRates(String courseId);
	void deleteByCourse(String courseId);

	
	/**Smart Algorithm
	 * @return 
	 * @throws IOException 
	 * @throws Exception **/
	public RecommendationEntity getRecommendations(long userNumber) throws IOException;
	public FileWriter writeToCsvFile(Map<String,ArrayList<RateEntity>>recomendationMap) throws IOException;
	public RecommendationEntity recommend(long userNumber) throws Exception;
	
	/**Graphs Methods**/
//	public List<GraphsEntity>getAllGraphs();
	List<GraphsEntity> getAllGraphs(String userId);
	List<CourseRateEntity> getAllRatesCourse();
	
 
}
