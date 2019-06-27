package TrainMe.TrainMe.FireBase.logic.JPA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;
import TrainMe.TrainMe.logic.entity.TrainerEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

@Service
public class FireBaseMethods implements IFireBase {
	private FirebaseOptions options;
	private FileInputStream serviceAccount;
	private String fileName = "src/main/resources/train-e0fc2-firebase-adminsdk-zm1mf-f441dd4bd4.json";

	private FirebaseDatabase database;
	private DatabaseReference ref;
	private DatabaseReference databaseReference;
	private DatabaseReference childReference;
	private FirebaseAuth firebaseAuth;
	private int currentNumOfUsers;
	private int maxNumOfUsers;
	private int position;
	private boolean generalFlag;
	private List<CourseEntity> courseList;
	private List<TrainerEntity> trainerList;
	private List<GeneralCourseEntity> generalCourseList;
	private UsersEntity myUser;

	@PostConstruct
	public void configure() {
		try {
			this.serviceAccount = new FileInputStream(this.fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://train-e0fc2.firebaseio.com").build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FirebaseApp.initializeApp(options);
		this.database = FirebaseDatabase.getInstance();
		this.firebaseAuth.getInstance();
		this.ref = database.getReference("server/saving-data/fireblog");
		this.databaseReference = database.getReference("/");
		this.generalFlag = false;
		this.courseList = new ArrayList<>();
		this.trainerList = new ArrayList<>();
		this.generalCourseList = new ArrayList<>();
	}

	@Override
	public TrainerEntity storeTrainer(TrainerEntity newTrainer) {
		this.childReference = databaseReference.child("Trainers").child(newTrainer.getId());
		CountDownLatch countDownLatch = new CountDownLatch(1);
		childReference.setValue(newTrainer, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
				// decrement countDownLatch value and application will be continues its
				// execution.
				countDownLatch.countDown();
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return newTrainer;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	@Override
	public void deleteByTrainer(TrainerEntity trainetToDelete) {
		this.childReference = databaseReference.child("Trainers").child(trainetToDelete.getId());
		childReference.removeValueAsync();
	}

	@Override
	public void deleteByTrainerId(String trainertId) {
		this.childReference = databaseReference.child("Trainers").child(trainertId);
		childReference.removeValueAsync();
	}

	@Override
	public GeneralCourseEntity addGeneralCourse(GeneralCourseEntity generalCourseEntity) {
		this.childReference = databaseReference.child("GeneralCourses").child(generalCourseEntity.getName());
		CountDownLatch countDownLatch = new CountDownLatch(1);
		childReference.setValue(generalCourseEntity, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
				// decrement countDownLatch value and application will be continues its
				// execution.
				countDownLatch.countDown();
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return generalCourseEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteByGeneralCourseName(String generalCourseName) {
		this.childReference = databaseReference.child("GeneralCourses").child(generalCourseName);
		childReference.removeValueAsync();
	}

	@Override
	public UsersEntity addUser(UsersEntity userEntity) {
		this.childReference = databaseReference.child("Users").child(userEntity.getUserId());
		CountDownLatch countDownLatch = new CountDownLatch(1);
		childReference.setValue(userEntity, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
				// decrement countDownLatch value and application will be continues its
				// execution.
				countDownLatch.countDown();
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return userEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteByUserId(String userId) {
		this.childReference = databaseReference.child("Users").child(userId);
		childReference.removeValueAsync();
	}

	@Override
	public CourseEntity addCourse(CourseEntity courseEntity) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.child("GeneralCourses").child(courseEntity.getCourseName()).exists()) {
					if (snapshot.child("Trainers").child((courseEntity.getTrainerId())).child("name").getValue()
							.toString().equals(courseEntity.getTrainerName())) {

						snapshot.child("Courses").child(courseEntity.getCourseId()).getRef().setValue(courseEntity,
								new CompletionListener() {
									@Override
									public void onComplete(DatabaseError error, DatabaseReference ref) {
										System.out.println("Record saved!");
										// decrement countDownLatch value and application will be continues its
										// execution.
										countDownLatch.countDown();
									}
								});
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});
		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return courseEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean checkCourseIsFull(String courseId) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				currentNumOfUsers = Integer.parseInt(snapshot.child("Courses").child(courseId)
						.child("currentNumOfUsersInCourse").getValue().toString());
				maxNumOfUsers = Integer.parseInt(
						snapshot.child("Courses").child(courseId).child("maxNumOfUsersInCourse").getValue().toString());
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
			}
		});
		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return currentNumOfUsers == maxNumOfUsers ? true : false;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isOnWaitingList(String courseId, String userId) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		generalFlag = false;
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.child("Courses").child(courseId).child("waitingList").child(userId).exists()) {
					generalFlag = true;
				} else {
					generalFlag = false;
				}
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
			}
		});
		try {
			countDownLatch.await();
			return generalFlag;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isUserRegistered(String courseId, String userId) {

		CountDownLatch countDownLatch = new CountDownLatch(1);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				generalFlag = false;
				if (snapshot.child("Courses").child(courseId).child("registered").child(userId).exists()) {
					generalFlag = true;
				} else {
					generalFlag = false;
				}
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
			}
		});
		try {

			countDownLatch.await();

			return generalFlag;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public int positionOnWaitingList(String courseId, String userId) {
		CountDownLatch countDownLatch = new CountDownLatch(1);

		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				countDownLatch.countDown();
				position = Integer.parseInt(snapshot.child("Courses").child(courseId).child("waitingList").child(userId)
						.child("position").getValue().toString());
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
			}
		});
		try {

			countDownLatch.await();
			return position;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	@Override
	public synchronized UsersEntity addUserToCourse(String courseId, UsersEntity userEntity) {
		this.childReference = databaseReference.child("Courses").child(courseId);
		CountDownLatch countDownLatch = new CountDownLatch(1);

		// Map map = new HashMap<String, Object>();

		Map map = new HashMap<String, Object>();
		map.put("user", userEntity);
		
		
		childReference.child("registered").child(userEntity.getUserId()).setValue(map, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
				//setCurrentNumOfUsersRegisteredToCourse(courseId, '+');
				countDownLatch.countDown();
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return userEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

//	@Override
//	public void setCurrentNumOfUsersRegisteredToCourse(String courseId,int newInt) {// char sign) {
//		System.err.println("In setCurrentNumOfUsersRegisteredToCourse");
//		CountDownLatch countDownLatch = new CountDownLatch(1);
//		
//		currentNumOfUsers = getCurrentNumOfUsersRegisteredToCourse(courseId);
//		System.err.println("AFTER GET");
//
////		if(sign=='+') {
////			System.err.println("In if(sign=='+')");
////			currentNumOfUsers++;
////		}else {
////			System.err.println("In ELSE if(sign=='+')");
////			currentNumOfUsers--;
////		}
//		currentNumOfUsers = newInt;
//		this.childReference = databaseReference.child("Courses").child(courseId);
//		childReference.child("currentNumOfUsersInCourse").setValue(String.valueOf(currentNumOfUsers),
//				new CompletionListener() {
//					@Override
//					public void onComplete(DatabaseError error, DatabaseReference ref) {
//						System.out.println("currentNumOfUsers Updated!");
//						countDownLatch.countDown();
//					}
//				});
//		try {
//			// wait for firebase to saves record.
//			countDownLatch.await();
//		} catch (InterruptedException ex) {
//			ex.printStackTrace();
//		}
//	}
	@Override
	public void setCurrentNumOfUsersRegisteredToCourse(String courseId, int newCurrentNumOfUsers) {
		currentNumOfUsers = newCurrentNumOfUsers;
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("Courses").child(courseId);
		childReference.child("currentNumOfUsersInCourse").setValue(String.valueOf(currentNumOfUsers),
				new CompletionListener() {
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						System.out.println("currentNumOfUsers Updated!");
						countDownLatch.countDown();
					}
				});
		try {
			// wait for firebase to saves record.
			countDownLatch.await();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public int getCurrentNumOfUsersRegisteredToCourse(String courseId) {

		CountDownLatch countDownLatch = new CountDownLatch(1);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				currentNumOfUsers = Integer.parseInt(snapshot.child("Courses").child(courseId)
						.child("currentNumOfUsersInCourse").getValue().toString());
				System.err.println("currentNumOfUsers= " + currentNumOfUsers);
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {

				// TODO Auto-generated method stub
			}
		});
		try {

			// wait for firebase to saves record.
			countDownLatch.await();

			return currentNumOfUsers;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	@Override
	public void deleteUserFromCourse(String courseId, String userId) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("registered").child(userId);
		//setCurrentNumOfUsersRegisteredToCourse(courseId, '-');
		childReference.removeValueAsync();
	}

	@Override
	public UsersEntity joinToWaitingList(String courseId, UsersEntity userEntity) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("waitingList")
				.child(userEntity.getUserId());
		CountDownLatch countDownLatch = new CountDownLatch(1);

		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				String pos = String
						.valueOf(snapshot.child("Courses").child(courseId).child("waitingList").getChildrenCount() + 1);
				Map map = new HashMap<String, Integer>();
				map.put("position", pos);
				childReference.setValue(map, new CompletionListener() {

					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						
						System.out.println("User is added to waiting list!");
						
					}
				});
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return userEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteUserFromWaitingList(String courseId, String userId) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("waitingList").child(userId);
		childReference.removeValueAsync();
	}

	@Override
	public void rateCourse(String courseId, String userId, int rate) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("rates").child(userId);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		Map map = new HashMap<String, Integer>();
		map.put(userId, rate);
		childReference.setValue(map, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Rate saved!");
				countDownLatch.countDown();
			}
		});
		try {
			// wait for firebase to saves record.
			countDownLatch.await();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean isDatePassed(String courseId) {
		generalFlag = false;
		CountDownLatch countDownLatch = new CountDownLatch(1);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				Date current_Date, course_Date;
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat mdFormat = new SimpleDateFormat("dd-MM-yyyy");
				String courseDateString = snapshot.child("Courses").child(courseId).child("date").getValue().toString();
				String currentDateString = mdFormat.format(calendar.getTime());
				try {
					current_Date = mdFormat.parse(currentDateString);
					course_Date = mdFormat.parse(courseDateString);
					if (course_Date.compareTo(current_Date) < 0) {
						generalFlag = true;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
			}
		});
		try {
			countDownLatch.await();
			return generalFlag;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<CourseEntity> getCoursesByDate(String date) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("Courses");
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					courseList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						if (ds.child("date").getValue().toString().equals(date)) {
							CourseEntity courseEntity = ds.getValue(CourseEntity.class);
							courseList.add(courseEntity);
						}
					}
				}
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});
		try {
			countDownLatch.await();
			return this.courseList;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public UsersEntity updateUser(UsersEntity newUser) {
		return this.addUser(newUser);
	}

	@Override
	public GeneralCourseEntity updateGeneralCourse(GeneralCourseEntity generalCourseEntity) {
		return this.addGeneralCourse(generalCourseEntity);
	}

	@Override
	public void writeHr(String courseId, String userId, List<Integer> hrList) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		// System.err.println(getUserById(userId).getGender());
		Map map = new HashMap<String, Object>();
		int avg = 0;
		if (hrList.size() >= 30) {
			int size = 30;
			int hrListNewSize = hrList.size() / size;
			int k = 0;
			Integer arr[] = new Integer[size];
			for (int i = 0; i < size; i++) {
				int avgIn = 0;
				boolean flag = true;
				for (int j = 0; j < hrListNewSize && flag == true; j++) {
					avgIn += hrList.get(k);
					k++;
					if (k >= hrList.size()) {
						flag = false;
					}
				}
				if (flag == false) {
					int temp = hrList.size() - (hrListNewSize * size);
					avgIn = avgIn / temp;
				} else {
					avgIn = avgIn / hrListNewSize;
				}
				arr[i] = avgIn;
			}
			for (int i = 0; i < size; i++) {
				avg += arr[i];
			}
			avg = avg / size;
			List<Integer> list = Arrays.asList(arr);

			map.put("HR_avg", avg);
			map.put("hrList", list);
		} else {
			for (int i = 0; i < hrList.size(); i++) {
				avg += hrList.get(i);
			}
			avg = avg / hrList.size();
			map.put("HR_avg", avg);
			map.put("hrList", hrList);
		}
		int c = calculateCalories(avg, userId);
		System.err.println("C " + c);
		map.put("calories", c);
		this.childReference = databaseReference.child("Courses").child(courseId).child("registered").child(userId);
		childReference.child("HR").setValue(map, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
				countDownLatch.countDown();
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<TrainerEntity> getAllTrainers() {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("Trainers");
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					trainerList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						TrainerEntity trainerEntity = ds.getValue(TrainerEntity.class);
						trainerList.add(trainerEntity);
					}
				}
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});
		try {
			countDownLatch.await();
			return this.trainerList;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public UsersEntity getUserById(String id) {
		System.err.println("in Start getUserById");

		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("Users").child(id);
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					String fullName = snapshot.child("fullName").getValue().toString();
					String gender = snapshot.child("gender").getValue().toString();
					String age = snapshot.child("age").getValue().toString();
					String weigh = snapshot.child("weigh").getValue().toString();
					myUser = new UsersEntity();
					myUser.setFullName(fullName);
					myUser.setGender(gender);
					myUser.setAge(age);
					myUser.setWeigh(weigh);
					System.err.println(myUser);
				}
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});
		try {
			countDownLatch.await();
			System.err.println("in end getUserById");

			return this.myUser;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public int calculateCalories(int avgHR, String userId) {
		UsersEntity user = getUserById(userId);
		int calories;
		int age = Integer.parseInt(user.getAge());
		float w = Float.parseFloat(user.getWeigh());
		int t = 60; // all the Courses is 1 hour
		calories = (int) ((0.4472 * avgHR - 0.05741 * w + 0.074 * age - 20.4022) * t / 4.184);
		if (user.getGender().equals("M")) {
			calories = (int) ((int) calories * 1.54);
		}
		return calories;
	}

	@Override
	public List<GeneralCourseEntity> getAllGeneralCourses() {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("GeneralCourses");
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					generalCourseList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						GeneralCourseEntity generalCourseEntity = ds.getValue(GeneralCourseEntity.class);
						generalCourseList.add(generalCourseEntity);
					}
				}
				countDownLatch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});
		try {
			countDownLatch.await();
			return this.generalCourseList;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public void getByTrainerId(String trainertId) {
//		CountDownLatch countDownLatch = new CountDownLatch(1);
//		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//			@Override
//			public void onDataChange(DataSnapshot snapshot) {
//				currentNumOfUsers = Integer.parseInt(snapshot.child("Courses").child(courseId)
//						.child("currentNumOfUsersInCourse").getValue().toString());
//				countDownLatch.countDown();
//			}
//
//			@Override
//			public void onCancelled(DatabaseError error) {
//				// TODO Auto-generated method stub
//			}
//		});
//		try {
//			// wait for firebase to saves record.
//			countDownLatch.await();
//			return currentNumOfUsers;
//		} catch (InterruptedException ex) {
//			ex.printStackTrace();
//			return -1;
//		}
	}
}