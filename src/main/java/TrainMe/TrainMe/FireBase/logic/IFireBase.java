package TrainMe.TrainMe.FireBase.logic;

import java.util.List;

import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;
import TrainMe.TrainMe.logic.entity.TrainerEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

public interface IFireBase {
	/**Trainer Methods**/
	public TrainerEntity storeTrainer(TrainerEntity newTrainer);
	public void deleteByTrainer(TrainerEntity trainetToDelete);
	public void deleteByTrainerId(String trainertId);
	
	/**General Course Methods**/
	public GeneralCourseEntity addCourse(GeneralCourseEntity generalCourseEntity);
	public void deleteByGeneralCourseName(String generalCourseName);

	/**User Methods**/
	public UsersEntity addUser(UsersEntity userEntity);
	public void deleteByUserId(String userId);
	public boolean isUserRegistered(String courseId,String userId);
	public UsersEntity addUserToCourse(String courseId, UsersEntity userEntity,int hrAVG,List<Integer>hrlist);
	public UsersEntity joinToWaitingList(String courseId,UsersEntity userEntity);
	public void deleteUserFromCourse(String courseId, String userId);
	public void deleteUserFromWaitingList(String courseId, String userId);
	
	/**Course Methods**/
	public CourseEntity addCourse(CourseEntity courseEntity);
	public boolean checkCourseIsFull(String courseId);
	public boolean isOnWaitingList(String courseId,String userId);
	public int positionOnWaitingList(String courseId,String userId);
	public void setCurrentNumOfUsersRegisteredToCourse(String courseId,int newCurrentNumOfUsers);
	public int getCurrentNumOfUsersRegisteredToCourse(String courseId);
	public void rateCourse(String courseId,String userId, int rate);
	public boolean isDatePassed(String courseId);
	public List<CourseEntity>getCoursesByDate(String date);
}
