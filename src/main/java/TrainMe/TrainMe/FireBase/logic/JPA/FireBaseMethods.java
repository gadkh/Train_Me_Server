package TrainMe.TrainMe.FireBase.logic.JPA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

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
	private final String fileName = "D://Final Project Server/train-e0fc2-firebase-adminsdk-zm1mf-f441dd4bd4.json";
	private FirebaseDatabase database;
	private DatabaseReference ref;
	private DatabaseReference databaseReference;
	private DatabaseReference childReference;
	private FirebaseAuth firebaseAuth;
	private int currentNumOfUsers;
	private int maxNumOfUsers;
	private boolean generalFlag;

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
	public GeneralCourseEntity addCourse(GeneralCourseEntity generalCourseEntity) {
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
				generalFlag=false;
				 if(snapshot.child("Courses").child(courseId).child("registered").child(userId).exists()){
					 generalFlag=true;				
					 }
				 else
				 {
					 generalFlag=false;
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
				int position = Integer.parseInt(snapshot.child("Courses").child(courseId).child("waitingList")
						.child(userId).child("position").getValue().toString());
				// TODO deal with the return here
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
			}
		});
		try {

			countDownLatch.await();
			return 1;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public UsersEntity addUserToCourse(String courseId, UsersEntity userEntity) {
		this.childReference = databaseReference.child("Courses").child(courseId);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		childReference.child("registered").child(userEntity.getUserId()).setValue(userEntity, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
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
		currentNumOfUsers = getCurrentNumOfUsersRegisteredToCourse(courseId);
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
		childReference.removeValueAsync();
	}

	@Override
	public UsersEntity joinToWaitingList(String courseId, UsersEntity userEntity) {
		this.childReference = databaseReference.child("Courses").child(courseId).child("waitingList")
				.child(userEntity.getUserId());
		CountDownLatch countDownLatch = new CountDownLatch(1);
		childReference.setValue(userEntity, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("User is added to waiting list!");
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

}