package TrainMe.TrainMe.FireBase.logic.JPA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

import javax.annotation.PostConstruct;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Query;
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
import TrainMe.TrainMe.logic.entity.CourseRateEntity;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;
import TrainMe.TrainMe.logic.entity.GraphsEntity;
import TrainMe.TrainMe.logic.entity.RateEntity;
import TrainMe.TrainMe.logic.entity.RecommendationEntity;
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
	private String courseNameForRecommendation;
	private String trainerNameForRecommendation;
	private String myCourseNum;
	private boolean generalFlag;
	private List<CourseEntity> courseList;
	private List<TrainerEntity> trainerList;
	private List<GeneralCourseEntity> generalCourseList;
	private List<UsersEntity> userList;
	private List<GraphsEntity>graphsList;
	private List<CourseRateEntity>courseRateList;
	private CourseRateEntity courseRate; 
	private UsersEntity myUser;
	private CourseEntity myCourse;
	private CourseRateEntity courseRateEntity;
	 

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
		this.userList= new ArrayList<>();
		this.graphsList= new ArrayList<>();
		this.courseRateList=new ArrayList<>();
	}

	@Override
	public TrainerEntity storeTrainer(TrainerEntity newTrainer) {
		this.childReference = databaseReference.child("Trainers");
		CountDownLatch countDownLatch = new CountDownLatch(2);
		childReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long trainerCount = 0;
				if (!snapshot.child("trainerCount").exists()) {
					trainerCount = 0;
				} else {
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
	public void deleteByCourse(String courseId) {
		this.childReference = databaseReference.child("Courses").child(courseId);
		childReference.removeValueAsync();
	}

	@Override
	public GeneralCourseEntity addGeneralCourse(GeneralCourseEntity generalCourseEntity) {
		this.childReference = databaseReference.child("GeneralCourses");
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long generalCourseCount = 0;
				if (!snapshot.child("generalCourseCount").exists()) {
					generalCourseCount = 0;
				} else {
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
				snapshot.child(generalCourseEntity.getName()).getRef().setValue(generalCourseEntity,
						new CompletionListener() {

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
				long userCount = 0;
				if (!snapshot.child("userCount").exists()) {
					userCount = 0;
				} else {
					userCount = (long) snapshot.child("userCount").getValue();
				}
				userCount++;
				userEntity.setUserNumber(userCount);
				snapshot.child("userCount").getRef().setValue(userCount, new CompletionListener() {

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
						GeneralCourseEntity generalCourseEntity = snapshot.child("GeneralCourses")
								.child(courseEntity.getCourseName()).getValue(GeneralCourseEntity.class);
						courseEntity.setDescription(generalCourseEntity.getDescription().toString());
						String createCourseNumber = generalCourseEntity.getGeneralCourseEntityNum() + ""
								+ courseEntity.getTrainerNumber();
						
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
	public UsersEntity addUserToCourse(String courseId, UsersEntity userEntity) {
		this.childReference = databaseReference.child("Courses").child(courseId);
		
		CountDownLatch countDownLatch = new CountDownLatch(2);
		CourseEntity courseEntity = getCourseById(courseId);
		Map map = new HashMap<String, Object>();
		map.put("user", userEntity);

		Map userCoursesMap = new HashMap<String, Object>();
		userCoursesMap.put("courseName", courseEntity.getCourseName());
		userCoursesMap.put("time", courseEntity.getTime());
		userCoursesMap.put("trainerName", courseEntity.getTrainerName());
		userCoursesMap.put("date", courseEntity.getDate());
		// if 2 or more users want to register to a course who is full - this line handle it
		if(!courseEntity.getCurrentNumOfUsersInCourse().equals(courseEntity.getMaxNumOfUsersInCourse()))
		{
		databaseReference.child("UserCourses").child(userEntity.getUserId()).child(courseId).setValue(userCoursesMap,
				new CompletionListener() {

					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						countDownLatch.countDown();
					}
				});

		childReference.child("registered").child(userEntity.getUserId()).setValue(map, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				deleteWaitingList(courseId);
				System.out.println("user added to course");
				countDownLatch.countDown();
			}
		});
		}
		else
		{
			countDownLatch.countDown();
		}
		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			int current = getCurrentNumOfUsersRegisteredToCourse(courseId);
			setCurrentNumOfUsersRegisteredToCourse(courseId, current);
			return userEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void setCurrentNumOfUsersRegisteredToCourse(String courseId, int newCurrentNumOfUsers) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
//		this.childReference = databaseReference.child("Courses").child(courseId);	
//		childReference.child("currentNumOfUsersInCourse").setValue(String.valueOf(currentNumOfUsers),
//				new CompletionListener() {
//					@Override
//					public void onComplete(DatabaseError error, DatabaseReference ref) {
//						currentNumOfUsers = newCurrentNumOfUsers;
//						countDownLatch.countDown();
//					}
//				});
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				String strNewCurrentNumOfUsers = "" + newCurrentNumOfUsers;
				snapshot.child("Courses").child(courseId).child("currentNumOfUsersInCourse").getRef()
						.setValue(strNewCurrentNumOfUsers, new CompletionListener() {

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
	public int getCurrentNumOfUsersRegisteredToCourse(String courseId) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				try {
					currentNumOfUsers = (int) snapshot.child("Courses").child(courseId).child("registered")
							.getChildrenCount();
					// .child("currentNumOfUsersInCourse").getValue().toString());
					countDownLatch.countDown();
				} catch (Exception e) {
					// TODO: handle exception
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
			return currentNumOfUsers;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	@Override
	public void deleteUserFromCourse(String courseId, String userId, String courseName) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("registered").child(userId);
		childReference.removeValueAsync();
		databaseReference.child("UserCourses").child(userId).child(courseId).removeValueAsync();
		int current = getCurrentNumOfUsersRegisteredToCourse(courseId);
		setCurrentNumOfUsersRegisteredToCourse(courseId, current);
	}

	@Override
	public UsersEntity joinToWaitingList(String courseId, UsersEntity userEntity) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("waitingList")
				.child(userEntity.getUserId());
		CountDownLatch countDownLatch = new CountDownLatch(2);

		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				String pos = String
						.valueOf(snapshot.child("Courses").child(courseId).child("waitingList").getChildrenCount() + 1);
				Map map = new HashMap<String, Object>();
				Map tokenMap = new HashMap<Object, Object>();
				tokenMap.put(userEntity.getToken().toString(),userEntity.getToken());
				map.put("position", pos);
				childReference.setValue(map, new CompletionListener() {

					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						countDownLatch.countDown();
					}
				});
				
				databaseReference.child("Courses").child(courseId).child("waitingList").child("tokens")
				.setValue(tokenMap, new CompletionListener() {
					
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
	public void deleteUserFromWaitingList(String courseId, String userId) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("waitingList").child(userId);
		childReference.removeValueAsync();
	}
	@Override
	public void deleteWaitingList(String courseId) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("waitingList");
		childReference.removeValueAsync();
	}
	
	
	@Override
	public void rateCourse(String courseId, String courseName, String userId, int rate) {
		RateEntity rateEntity = new RateEntity();
		this.childReference = databaseReference.child("Rate").child(userId);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		UsersEntity userEntity = getUserById(userId);
		CourseEntity courseEntity = getCourseById(courseId);
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {

				rateEntity.setCourseName(courseEntity.getCourseName());
				rateEntity.setRate(rate);
				rateEntity.setTrainerName(courseEntity.getTrainerName());
				rateEntity.setUserId(userId);
				rateEntity.setCourseNumber(courseEntity.getCourseNum());
				rateEntity.setUserNumber(userEntity.getUserNumber());

				snapshot.child("" + courseEntity.getCourseNum()).getRef().setValue(rateEntity,
						new CompletionListener() {

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
		
		this.databaseReference.child("CourseRate").child(String.valueOf(courseEntity.getCourseNum()))
		.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				
				if(snapshot.exists())
				{
					System.err.println("1");
					courseRateEntity=snapshot.getValue(CourseRateEntity.class);
					//rateList=(ArrayList<Integer>)snapshot.child("rates").getValue();
					System.err.println("2");
					//System.err.println(rateList.size());
				}
				else
				{
					courseRateEntity=new CourseRateEntity();
					courseRateEntity.setCourseName(courseEntity.getCourseName());
					courseRateEntity.setTrainerName(courseEntity.getTrainerName());
					courseRateEntity.setMaxNumOfUsers(courseEntity.getMaxNumOfUsersInCourse());
					courseRateEntity.setNumOfUsers(courseEntity.getCurrentNumOfUsersInCourse());
					courseRateEntity.setCourseNum(courseEntity.getCourseNum());
				}
				courseRateEntity.getRates().add(rate);
				
				courseRateEntity.setAvg(0);
				for(int i=0;i<courseRateEntity.getRates().size();i++)
				{
					courseRateEntity.setAvg(courseRateEntity.getAvg()+courseRateEntity.getRates().get(i));
				}
				courseRateEntity.setAvg(courseRateEntity.getAvg()/courseRateEntity.getRates().size());
			
				snapshot.getRef().setValue(courseRateEntity, new CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						System.err.println("5");
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
			System.err.println("6");
			System.err.println("CountDowwn "+countDownLatch.getCount());
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
		System.err.println(date);
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
		GraphsEntity graphsEntity=new GraphsEntity();
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
			
			graphsEntity.setHrList(list);
			//map.put("HR_avg", avg);
			//map.put("hrList", list);
		} else {
			for (int i = 0; i < hrList.size(); i++) {
				avg += hrList.get(i);
			}
			avg = avg / hrList.size();
			

			//map.put("HR_avg", avg);
			//map.put("hrList", hrList);
			graphsEntity.setHrList(hrList);
		}
		graphsEntity.setHR_avg(avg);
		int c = calculateCalories(avg, userId);
		graphsEntity.setCalories(c);
		//map.put("calories", c);
		graphsEntity.setDate(courseEntite.getDate());
		graphsEntity.setCourseName(courseEntite.getCourseName());
		
		//map.put("date", courseEntite.getDate());
		//map.put("courseName", courseEntite.getCourseName());
		this.childReference = databaseReference.child("Graphs").child(userId).child(courseEntite.getCourseId());
		childReference.setValue(graphsEntity, new CompletionListener() {

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
						if (!ds.getKey().equals("trainerCount")) {
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
	public List<UsersEntity> getAllUsers() {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("Users");
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					userList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						if(!ds.getKey().equals("userCount"))
						{
							UsersEntity userEntity = ds.getValue(UsersEntity.class);
							userList.add(userEntity);
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
			return this.userList;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<GraphsEntity> getAllGraphs(String userId) {
		this.graphsList.clear();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("Graphs").child(userId);
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					for (DataSnapshot ds : snapshot.getChildren()) {
							GraphsEntity graphsEntity = ds.getValue(GraphsEntity.class);
							graphsList.add(graphsEntity);
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
			return this.graphsList;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	public CourseRateEntity getCourseRates(String courseId) {
		CourseEntity courseEntity=getCourseById(courseId);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		this.childReference = databaseReference.child("CourseRate").child(String.valueOf(courseEntity.getCourseNum()));
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
						courseRate= snapshot.getValue(CourseRateEntity.class);
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
			return courseRate;
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

					myUser = snapshot.getValue(UsersEntity.class);
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
						if (!ds.getKey().equals("generalCourseCount")) {
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
	public List<CourseRateEntity> getAllRatesCourse() {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		courseRateList.clear();
		this.childReference = databaseReference.child("CourseRate");
		this.childReference.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					for (DataSnapshot ds : snapshot.getChildren()) {
						CourseRateEntity courseRateEntity = ds.getValue(CourseRateEntity.class);
						courseRateList.add(courseRateEntity);
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
			return this.courseRateList;
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
					myCourse = snapshot.getValue(CourseEntity.class);

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

	@Override
	public RecommendationEntity getRecommendations(long userNumber) throws IOException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		Map<String, ArrayList<RateEntity>> userRecommendationMap = new HashMap();
		databaseReference.child("Rate").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {
					for (DataSnapshot ds : snapshot.getChildren()) {
						ArrayList<RateEntity> recommendationList = new ArrayList();
						for (DataSnapshot ds2 : ds.getChildren()) {
							RateEntity recommendation = ds2.getValue(RateEntity.class);
							recommendationList.add(recommendation);
						}
						userRecommendationMap.put(recommendationList.get(0).getUserId(), recommendationList);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			countDownLatch.await();
			FileWriter fw = writeToCsvFile(userRecommendationMap);
			return recommend(userNumber);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FileWriter writeToCsvFile(Map<String, ArrayList<RateEntity>> recomendationMap) throws IOException {
		FileWriter writer = new FileWriter("data/dataset.csv");
		for (Map.Entry<String, ArrayList<RateEntity>> entry : recomendationMap.entrySet()) {
			for (int i = 0; i < entry.getValue().size(); i++) {
				writer.write("" + entry.getValue().get(i).getUserNumber() + ",");
				writer.write("" + entry.getValue().get(i).getCourseNumber() + ",");
				writer.write("" + entry.getValue().get(i).getRate());
				writer.write("\n");
			}
		}
		writer.flush();
		writer.close();
		return writer;
	}

	@Override
	public RecommendationEntity recommend(long userNumber) throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		DataModel model = new FileDataModel(new File("data/dataset.csv"));
		UserSimilarity similarity;
		similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			List<RecommendedItem> recommendations = recommender.recommend(userNumber, 1);
//		List<RecommendedItem> recommendations = recommender.recommend(2, 1);

		if (!recommendations.isEmpty()) {
			myCourseNum = String.valueOf(recommendations.get(0).getItemID());
		}
		databaseReference.child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot ds : snapshot.getChildren()) {
					if (ds.child("courseNum").getValue().toString().equals(myCourseNum)) {
						trainerNameForRecommendation = ds.child("trainerName").getValue().toString();
						courseNameForRecommendation = ds.child("courseName").getValue().toString();
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
			return new RecommendationEntity(courseNameForRecommendation, trainerNameForRecommendation);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
}