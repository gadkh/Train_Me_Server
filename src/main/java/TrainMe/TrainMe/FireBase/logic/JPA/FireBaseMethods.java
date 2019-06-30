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
import javax.annotation.processing.Completion;

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
	private CourseEntity myCourse;
	
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
		this.childReference = databaseReference.child("Trainers");
		CountDownLatch countDownLatch = new CountDownLatch(2);
		childReference.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long trainerCount=0;
				if(!snapshot.child("trainerCount").exists())
				{
					trainerCount=0;
				}
				else
				{
					trainerCount = (long) snapshot.child("trainerCount").getValue();
				}
				trainerCount++;
				newTrainer.setTrainerNum(trainerCount);
				snapshot.child("trainerCount").getRef().setValue(trainerCount, new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						countDownLatch.countDown();
					}
				});
				snapshot.child(newTrainer.getId()).getRef().setValue(newTrainer, new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						// TODO Auto-generated method stub
						countDownLatch.countDown();
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
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
		this.childReference = databaseReference.child("GeneralCourses");
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long generalCourseCount=0;
				if(!snapshot.child("generalCourseCount").exists())
				{
					generalCourseCount=0;
				}
				else
				{
					generalCourseCount = (long) snapshot.child("generalCourseCount").getValue();
				}
				generalCourseCount++;
				generalCourseEntity.setGeneralCourseEntityNum(generalCourseCount);
				snapshot.child("generalCourseCount").getRef().setValue(generalCourseCount, new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						countDownLatch.countDown();
					}
				});
				snapshot.child(generalCourseEntity.getName()).getRef().setValue(generalCourseEntity, new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						// TODO Auto-generated method stub
						countDownLatch.countDown();
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
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
		this.childReference = databaseReference.child("Users");
		CountDownLatch countDownLatch = new CountDownLatch(2);
		childReference.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long userCount=0;
				if(!snapshot.child("userCount").exists())
				{
					userCount=0;
				}
				else
				{
					userCount = (long) snapshot.child("userCount").getValue();
				}
				userCount++;
				userEntity.setUserNumber(userCount);
				snapshot.child("userCount").getRef().setValue(userCount,  new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						countDownLatch.countDown();
					}
				});
				snapshot.child(userEntity.getUserId()).getRef().setValue(userEntity, new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						// TODO Auto-generated method stub
						countDownLatch.countDown();
					}
				});
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
						GeneralCourseEntity generalCourseEntity=snapshot.child("GeneralCourses").child(courseEntity.getCourseName()).getValue(GeneralCourseEntity.class);
						courseEntity.setDescription(generalCourseEntity.getDescription().toString());
						String createCourseNumber=generalCourseEntity.getGeneralCourseEntityNum()+""+courseEntity.getTrainerNumber();
						courseEntity.setCourseNum(Long.parseLong(createCourseNumber));
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


	@Override
	public void setCurrentNumOfUsersRegisteredToCourse(String courseId, int newCurrentNumOfUsers) {
		currentNumOfUsers = newCurrentNumOfUsers;
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("Courses").child(courseId);
		childReference.child("currentNumOfUsersInCourse").setValue(String.valueOf(currentNumOfUsers),
				new CompletionListener() {
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
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
	public void rateCourse(String courseId, String courseName, String userId, int rate) {
		this.childReference = databaseReference.child("Rate").child(userId);
		CountDownLatch countDownLatch = new CountDownLatch(2);
		UsersEntity userEntity=getUserById(userId);
		CourseEntity courseEntity=getCourseById(courseId);
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {
		
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				snapshot.child("userNumber").getRef().setValue(userEntity.getUserNumber(),new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						countDownLatch.countDown();	

					}
				});
				Map map = new HashMap<String, Object>();
				map.put("rate", rate);
				map.put("courseName", courseEntity.getCourseName());
				map.put("trainerName", courseEntity.getTrainerName());
				map.put("courseNumber", courseEntity.getCourseNum());
				snapshot.child("Courses").child(""+courseEntity.getCourseNum()).getRef().setValue(map, new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						countDownLatch.countDown();

					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
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
	public void writeHr(CourseEntity courseEntite, String userId, List<Integer> hrList) {
		
		CountDownLatch countDownLatch = new CountDownLatch(1);
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
		map.put("calories", c);
		map.put("date", courseEntite.getDate());
		map.put("courseName", courseEntite.getCourseName());
		this.childReference = databaseReference.child("Graphs").child(userId).child(courseEntite.getCourseId());
		childReference.setValue(map, new CompletionListener() {

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
						if(!ds.getKey().equals("trainerCount"))
						{
							TrainerEntity trainerEntity = ds.getValue(TrainerEntity.class);
							trainerList.add(trainerEntity);
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
			return this.trainerList;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public UsersEntity getUserById(String id) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		databaseReference.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {

					myUser=snapshot.getValue(UsersEntity.class);
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
						if(!ds.getKey().equals("generalCourseCount"))
						{
							GeneralCourseEntity generalCourseEntity = ds.getValue(GeneralCourseEntity.class);
							generalCourseList.add(generalCourseEntity);
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
			return this.generalCourseList;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public CourseEntity getCourseById(String courseId) {

		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		databaseReference.child("Courses").child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					myCourse=snapshot.getValue(CourseEntity.class);
					
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

			return this.myCourse;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}