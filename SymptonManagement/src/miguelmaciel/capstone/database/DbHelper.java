package miguelmaciel.capstone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DbHelper extends SQLiteOpenHelper {
	//SQLite DataBase
	private SQLiteDatabase mDB;
	// ***** Version *****
	private static final int DATABASE_VERSION = 1;
	
	// ***** DB Name *****
	private static final String SymptonDB = "sympton.db";
	
	
	
	// ***** Tables *****
	public static final String tbPatients = "Patients";
	public static final String tbDoctors = "Doctors";
	public static final String tbDoctorPatients = "DoctorPatients";
	public static final String tbMessageBoard = "MessageBoard";
	public static final String tbMedicine = "Medicine";
	public static final String tbPatientCheckIn = "PatientCheckIn";
	public static final String tbPatientMedication = "PatientMedication";
	public static final String tbCheckInMedication = "CheckInMedication";
	public static final String tbAlarmingSymptons = "AlarmingSymptons";
	
	public static final String tbAlarmTimes = "AlarmTimes";
	public static final String tbSystemPrefs = "SystemPrefs";
	public static final String tbChat = "Chat";
	public static final String tbAlertMedication = "AlertMedication";
	
	
	
	// ***** FIELDS *****
	// Table PATIENTS
	public static final String IDPatient = "_id";
	public static final String FirstName = "FirstName";
	public static final String LastName = "LastName";
	public static final String DateOfBirth = "DateOfBirth";
	public static final String Email = "Email";
	public static final String UserName = "UserName";
	public static final String PassWord = "PassWord";
	
	// Table DOCTORS
	public static final String IDDoctor = "_id";	
	public static final String Specialization = "Specialization";	
	
	// Table DOCTORPATIENTS
	public static final String Doctor = "Doctor";
	public static final String Patient = "Patient";
	
	// Table MessageBoard
	public static final String IDMessage = "_id";
	public static final String Date = "Date";
	public static final String Type = "Type";
	public static final String Message = "Message";
	public static final String Photo = "Photo";
	public static final String OpenWhat = "OpenWhat";
	

	// Table Medicine
	public static final String IDMedicine = "_id";
	public static final String Name = "Name";
	public static final String Composition = "Composition";
	
	
	// Table PatientCheckIn
	public static final String IDCheckIn = "_id";
	public static final String DateCheckIn = "DateCheckIn";
	public static final String State = "State";
	public static final String ThroatLevel = "ThroatLevel";
	public static final String PainLevel = "PainLevel";
	public static final String TakeMed = "TakeMed";
	
	
	// Table PatientMedication
	public static final String IDMedication = "_id";
	public static final String StartDate = "StartDate";
	public static final String EndDate = "EndDate";
	public static final String Dose = "Dose";
	public static final String Active = "Active";
	public static final String Observations = "Observations";
	public static final String Medicine = "Medicine";


	// Table CheckInMedication
	public static final String CheckIn = "CheckIn";
	public static final String PatientMedication = "PatientMedication";
	public static final String TakeState = "TakeState";
	public static final String Time = "Time";


	// Table AlarmingSymptons
	public static final String IDAlarmingSymptons = "_id";
	public static final String Sympton = "Sympton";
	public static final String Situation = "Situation";
	
	// Table SystemPrefs
	public static final String TypeNotification = "TypeNotification";
	
	// Table Chat
	public static final String SendFrom = "SendFrom";	
	
	//Table AlarmTimes
	public static final String AlarmTime = "AlarmTime";
		
	private static final String DATABASE_CREATE_tbSystemPrefs = "create table "
			+ tbSystemPrefs + "( " 
			+ TypeNotification + " text, "
			+ Patient + " integer, "			
			+ "FOREIGN KEY (" + Patient + ") REFERENCES " + tbPatients + " (" + IDPatient + "));";

	
	private static final String DATABASE_CREATE_tbChat = "create table "
			+ tbChat + "( " + SendFrom 
			+ " text, " 
			+ Date + " text, " 
			+ Message + " text, " 
			+ Patient + " integer, " 
			+ Doctor + " integer, "
			+ "FOREIGN KEY (" + Doctor + ") REFERENCES " + tbDoctors	+ " ("	+ IDDoctor + "), "
			+ "FOREIGN KEY (" + Patient + ") REFERENCES " + tbPatients + " (" + IDPatient + "));";
	
	
	private static final String DATABASE_CREATE_tbAlarmTime = "create table "
			+ tbAlarmTimes + "( " 
			+ AlarmTime + " text, "
			+ Patient + " integer, "			
			+ "FOREIGN KEY (" + Patient + ") REFERENCES " + tbPatients + " (" + IDPatient + "));";
	
	
	// DATABASE IMPLEMENTATION
	// Patient
	private static final String DATABASE_CREATE_tbPatients = "create table "
				+ tbPatients + "( " + IDPatient
				+ " integer primary key autoincrement, " 
				+ FirstName + " text, " 
				+ LastName + " text, " 
				+ DateOfBirth + " text, "
				+ Email + " text, "
				+ UserName + " text, "
				+ PassWord + " text);";
	
	
	// Doctor
	private static final String DATABASE_CREATE_tbDoctors = "create table "
				+ tbDoctors + "( " + IDDoctor
				+ " integer primary key autoincrement, " 
				+ FirstName + " text, " 
				+ LastName + " text, "
				+ Email + " text, "
				+ Specialization + " text, "
				+ UserName + " text, "
				+ PassWord + " text);";
	
	// DoctorPatients
	private static final String DATABASE_CREATE_tbDoctorPatients = "create table "
			+ tbDoctorPatients + "( " + Doctor	+ " integer, "
			+ Patient + " integer, "
			+ "FOREIGN KEY (" + Doctor + ") REFERENCES " + tbDoctors	+ " ("	+ IDDoctor + "), "
			+ "FOREIGN KEY (" + Patient + ") REFERENCES " + tbPatients + " (" + IDPatient + "));";
	
	// MessageBoard
	private static final String DATABASE_CREATE_tbMessageBoard = "create table "
			+ tbMessageBoard + "( " 
			+ IDMessage + " integer primary key autoincrement, "
			+ Date + " text, " 
			+ Type + " text, " 
			+ Message + " text, " 
			+ Photo + " text, " 
			+ OpenWhat + " text, "
			+ Patient + " integer, " 
			+ Doctor + " integer, "
			+ "FOREIGN KEY (" + Doctor + ") REFERENCES " + tbDoctors	+ " ("	+ IDDoctor + "), "
			+ "FOREIGN KEY (" + Patient + ") REFERENCES " + tbPatients + " (" + IDPatient + "));";
	
	
	// Medicine
	private static final String DATABASE_CREATE_tbMedicine = "create table "
				+ tbMedicine + "( " + IDMedicine
				+ " integer primary key autoincrement, " 
				+ Name + " text, "				
				+ Composition + " text);";
	
	
	// PatientCheckIn
	private static final String DATABASE_CREATE_tbPatientCheckIn = "create table "
			+ tbPatientCheckIn + "( " 
			+ IDCheckIn + " integer primary key autoincrement, "
			+ DateCheckIn + " text, " 
			+ State + " text, " 
			+ ThroatLevel + " text, " 
			+ PainLevel + " text, " 
			+ TakeMed + " text, "
			+ Patient + " integer, "			
			+ "FOREIGN KEY (" + Patient + ") REFERENCES " + tbPatients + " (" + IDPatient + "));";
	
	
	
	// PatientMedication
	private static final String DATABASE_CREATE_tbPatientMedication = "create table "
			+ tbPatientMedication + "( " 
			+ IDMedication + " integer primary key autoincrement, "
			+ StartDate + " text, " 
			+ EndDate + " text, " 
			+ Dose + " text, " 
			+ Active + " text, " 
			+ Observations + " text, "
			+ Patient + " integer, "	
			+ Doctor + " integer, "
			+ Medicine + " integer, "
			+ "FOREIGN KEY (" + Patient + ") REFERENCES " + tbPatients	+ " ("	+ IDPatient + "), "
			+ "FOREIGN KEY (" + Doctor + ") REFERENCES " + tbDoctors	+ " ("	+ IDDoctor + "), "
			+ "FOREIGN KEY (" + Medicine + ") REFERENCES " + tbMedicine + " (" + IDMedicine + "));";
	
	// CheckInMedication
	private static final String DATABASE_CREATE_tbCheckInMedication = "create table "
			+ tbCheckInMedication + "( " 
			+ CheckIn + " integer, "
			+ PatientMedication + " integer, "
			+ TakeState + " text, " 
			+ Date + " text, " 
			+ Time + " text, "
			+ "FOREIGN KEY (" + CheckIn + ") REFERENCES " + tbPatientCheckIn	+ " ("	+ IDCheckIn + "), "
			+ "FOREIGN KEY (" + PatientMedication + ") REFERENCES " + tbPatientMedication + " (" + IDMedication + "));";
	
	// AlarmingSymptons
	private static final String DATABASE_CREATE_tbAlarmingSymptons = "create table "
			+ tbAlarmingSymptons + "( " 
			+ IDAlarmingSymptons + " integer primary key autoincrement, "
			+ Sympton + " text, " 
			+ Situation + " text, " 
			+ Date + " text, "
			+ CheckIn + " integer, "
			+ "FOREIGN KEY (" + CheckIn + ") REFERENCES " + tbPatientCheckIn + " (" + IDCheckIn + "));";
			
	// AlertMedication
	private static final String DATABASE_CREATE_tbAlertMedication = "create table "
			+ tbAlertMedication + "( " 
			+ Message + " text, "
			+ Date + " text, "
			+ Patient + " integer, " 
			+ Doctor + " integer, " 
			+ PatientMedication + " integer, "
			+ "FOREIGN KEY (" + Patient + ") REFERENCES " + tbPatients	+ " ("	+ IDPatient + "), "
			+ "FOREIGN KEY (" + Doctor + ") REFERENCES " + tbDoctors	+ " ("	+ IDDoctor + "), "
			+ "FOREIGN KEY (" + PatientMedication + ") REFERENCES " + tbPatientMedication + " (" + IDMedication + "));";

	
	private static DbHelper sInstance;
	public static DbHelper getInstance(Context context) {
	    // Use the application context, which will ensure that you 
	    // don't accidentally leak an Activity's context.
	    // See this article for more information: http://bit.ly/6LRzfx
	    if (sInstance == null) {
	      sInstance = new DbHelper(context.getApplicationContext());
	    }
	    return sInstance;
	  }
	  
	public DbHelper(Context context) {
		super(context, SymptonDB, null, DATABASE_VERSION);
		this.mDB = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_tbPatients);
		db.execSQL(DATABASE_CREATE_tbDoctors);
		db.execSQL(DATABASE_CREATE_tbDoctorPatients);
		db.execSQL(DATABASE_CREATE_tbMessageBoard);
		db.execSQL(DATABASE_CREATE_tbMedicine);
		db.execSQL(DATABASE_CREATE_tbPatientCheckIn);
		db.execSQL(DATABASE_CREATE_tbPatientMedication);
		db.execSQL(DATABASE_CREATE_tbCheckInMedication);
		db.execSQL(DATABASE_CREATE_tbAlarmingSymptons);
		db.execSQL(DATABASE_CREATE_tbAlarmTime);
		db.execSQL(DATABASE_CREATE_tbSystemPrefs);
		db.execSQL(DATABASE_CREATE_tbChat);
		db.execSQL(DATABASE_CREATE_tbAlertMedication);
		
		//Patients
		db.execSQL("INSERT INTO " + DbHelper.tbPatients +" VALUES (1,'Miguel', 'Maciel', ' ', 'Miguel@hotmail.com', '1', '1')");
		db.execSQL("INSERT INTO " + DbHelper.tbPatients +" VALUES (2,'Happy', 'Amorim', ' ', 'Happy@gmail.pt', '2', '2')");
		
		//Doctors
		db.execSQL("INSERT INTO " + DbHelper.tbDoctors +" VALUES (1,'Dr Joao', 'Meireles', ' ', 'Clinica Geral', '1', '1')");
		db.execSQL("INSERT INTO " + DbHelper.tbDoctors +" VALUES (2,'Dr Rui', 'Malez', ' ', 'Eye Specialist', '2', '2')");
		
		//Relation Doctor - Patient
		db.execSQL("INSERT INTO " + DbHelper.tbDoctorPatients +" VALUES (1,1)");
		db.execSQL("INSERT INTO " + DbHelper.tbDoctorPatients +" VALUES (2,1)");
		db.execSQL("INSERT INTO " + DbHelper.tbDoctorPatients +" VALUES (2,2)");
		
		//Medicine
		db.execSQL("INSERT INTO " + DbHelper.tbMedicine +" VALUES (1,'Lortab', '50mg')");
		db.execSQL("INSERT INTO " + DbHelper.tbMedicine +" VALUES (2,'OxyContin', '100mg')");
		db.execSQL("INSERT INTO " + DbHelper.tbMedicine +" VALUES (3,'Paracetemol', '75mg')");
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + tbPatients);
		db.execSQL("DROP TABLE IF EXISTS " + tbDoctors);
		db.execSQL("DROP TABLE IF EXISTS " + tbDoctorPatients);
		db.execSQL("DROP TABLE IF EXISTS " + tbMessageBoard);
		db.execSQL("DROP TABLE IF EXISTS " + tbMedicine);
		db.execSQL("DROP TABLE IF EXISTS " + tbPatientCheckIn);
		db.execSQL("DROP TABLE IF EXISTS " + tbPatientMedication);
		db.execSQL("DROP TABLE IF EXISTS " + tbCheckInMedication);
		db.execSQL("DROP TABLE IF EXISTS " + tbAlarmingSymptons);
		db.execSQL("DROP TABLE IF EXISTS " + tbAlarmTimes);
		db.execSQL("DROP TABLE IF EXISTS " + tbSystemPrefs);
		db.execSQL("DROP TABLE IF EXISTS " + tbChat);
		db.execSQL("DROP TABLE IF EXISTS " + tbAlertMedication);
		onCreate(db);
	}
	
	
	//Methods RAW
	public Cursor getAlarmTimes(long idPatient) {
		return mDB.rawQuery("select * from AlarmTimes where Patient = " + idPatient, null);
	}
	
	public Cursor getSystemPreferences(long idPatient) {
		return mDB.rawQuery("select * from SystemPrefs Where Patient = " + idPatient, null);
	}
	
	public String getNotificationTypeByPatientID(long idPatient) {
		String sql = "SELECT TypeNotification FROM SystemPrefs WHERE Patient = " + idPatient + ";";
		SQLiteStatement s = mDB.compileStatement(sql);
		String dateTime = s.simpleQueryForString();
		return dateTime;
	}
	
	public void createNotification(long idPatient, String typeNotification) {
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.Patient, idPatient);
		cv.put(DbHelper.TypeNotification, typeNotification);

		mDB.insert(DbHelper.tbSystemPrefs, null, cv);
	}
	
	public void deleteNotifications(long idPatient) {
		mDB.delete(DbHelper.tbSystemPrefs, Patient + " = " + idPatient, null);
	}	
	
	public void createAlarm(long idPatient, String alarmTime) {
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.AlarmTime, alarmTime);
		cv.put(DbHelper.Patient, idPatient);

		mDB.insert(DbHelper.tbAlarmTimes, null, cv);
	}
	
	public void deleteAlarms(long idPatient) {
		mDB.delete(DbHelper.tbAlarmTimes, DbHelper.Patient + " = " + idPatient, null);
	}
	
}
