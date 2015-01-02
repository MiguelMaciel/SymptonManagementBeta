package miguelmaciel.capstone.symptonmanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.doctors.DoctorPatientFile;
import miguelmaciel.capstone.repositorys.AlarmingSymptons;
import miguelmaciel.capstone.repositorys.CheckIn;
import miguelmaciel.capstone.repositorys.CheckInMedication;
import miguelmaciel.capstone.services.AlarmingSymptonsSvc;
import miguelmaciel.capstone.services.AlarmingSymptonsSvcApi;
import miguelmaciel.capstone.services.CheckInMedicationSvc;
import miguelmaciel.capstone.services.CheckInMedicationSvcApi;
import miguelmaciel.capstone.services.CheckInSvc;
import miguelmaciel.capstone.services.CheckInSvcApi;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CheckInMessage extends ActionBarActivity {
	static Button btnAttach, btnCheckIn;
	static ImageView imgPhoto;
	static EditText etObs;
	static String throatLevel, eatLevel, takeMeds;
	static Long idCheckIn, idPatient;
	static ArrayList<String> arrayID = new ArrayList<String>();
	static ArrayList<String> arrayState = new ArrayList<String>();
	static ArrayList<String> arrayDate = new ArrayList<String>();
	static ArrayList<String> arrayTime = new ArrayList<String>();
	static String typeOfCheckIn, userType;
	static Collection<CheckInMedication> colCheckMed;
	static CheckIn checkInPub;

	// Photo variables
	public Uri imageURI = null;
	static int TAKE_PICTURE = 1;
	static int GET_PICTURE = 2;
	Uri uriSavedImage;
	public static final int SELECT_FOLDER = 1;
	public static final int MEDIA_TYPE_IMAGE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}
		setContentView(R.layout.activity_check_in_message);

		typeOfCheckIn = getIntent().getStringExtra("type");
		idCheckIn = Long.parseLong(getIntent().getStringExtra("idCheckIn"));
		idPatient = Long.parseLong(getIntent().getStringExtra("idPatient"));
		throatLevel = getIntent().getStringExtra("throat");
		eatLevel = getIntent().getStringExtra("eat");
		takeMeds = getIntent().getStringExtra("meds");

		if (typeOfCheckIn.equals("data")) {
			userType = getIntent().getStringExtra("userType");
		}

		if (takeMeds.equals("1")) {
			arrayID = getIntent().getStringArrayListExtra("ids");
			arrayState = getIntent().getStringArrayListExtra("state");
			arrayDate = getIntent().getStringArrayListExtra("date");
			arrayTime = getIntent().getStringArrayListExtra("time");
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		if (savedInstanceState != null) {
			imageURI = savedInstanceState.getParcelable("imageURI");
			imgPhoto.setImageURI(imageURI);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putParcelable("imageURI", imageURI);
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_check_in_message, container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			etObs = (EditText) view
					.findViewById(R.id.et_CheckInMessage_Observations);
			btnAttach = (Button) view
					.findViewById(R.id.btn_CheckInMessage_Attach);
			btnCheckIn = (Button) view
					.findViewById(R.id.btn_CheckInMessage_CheckIn);

			imgPhoto = (ImageView) view
					.findViewById(R.id.img_CheckInMessage_Image);

			btnAttach.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					selImage(view);
				}
			});

			btnCheckIn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						if (typeOfCheckIn.equals("data")) {
							if (userType.equals("doctor-sympton")) {
								Intent patientFile = new Intent(getView()
										.getContext(), DoctorPatientFile.class);
								patientFile.putExtra("backType", "home");
								patientFile.putExtra("idPatient",
										idPatient.toString());
								startActivity(patientFile);
							}
						} else {
							getCheckInMedicationList();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			if (typeOfCheckIn.equals("data")) {
				new DownloadImage().execute();
				disableTexts();
				if (userType.equals("doctor-sympton")) {
					btnCheckIn.setText("Check Patient File");
					btnAttach.setVisibility(View.GONE);
				} else if (userType.equals("patient")) {
					btnCheckIn.setVisibility(View.GONE);
					btnAttach.setVisibility(View.GONE);
				} else {
					btnCheckIn.setVisibility(View.GONE);
					btnAttach.setVisibility(View.GONE);
				}
				getCheckInData();
			}
		}

		private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
			// Downloads bitmap in an AsyncTask background thread.
			protected Bitmap doInBackground(Void... urls) {
				return displayImage();
			}

			// Set the image on the imageview
			protected void onPostExecute(Bitmap image) {
				if (image != null) {
					imgPhoto.setImageBitmap(image);
				}
			}
		}

		public void selImage(final View view) {
			// Open chooser
			AlertDialog levelDialog;
			final CharSequence[] types = { "Select from folder",
					"Take from camera" };
			AlertDialog.Builder builder = new AlertDialog.Builder(
					view.getContext());
			builder.setTitle("Choose an option!");
			builder.setSingleChoiceItems(types, -1,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							switch (item) {
							case 0:
								try {
									Intent intentFolder = new Intent();
									intentFolder.setType("image/*");
									intentFolder
											.setAction(Intent.ACTION_GET_CONTENT);
									Uri fileNameFolder = getOutputMediaFileUri(SELECT_FOLDER);
									intentFolder.putExtra(
											MediaStore.EXTRA_OUTPUT,
											fileNameFolder);
									startActivityForResult(Intent
											.createChooser(intentFolder,
													"Select Image"),
											GET_PICTURE);
								} catch (Exception e) {
									e.printStackTrace();
									Toast.makeText(
											view.getContext(),
											R.string.it_s_not_possible_open_the_folder_,
											Toast.LENGTH_LONG).show();
								}
								break;
							case 1:
								try {
									Intent intentCamera = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									Uri fileNameCamera = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
									intentCamera.putExtra(
											MediaStore.EXTRA_OUTPUT,
											fileNameCamera);
									startActivityForResult(intentCamera,
											TAKE_PICTURE);
								} catch (Exception e) {
									e.printStackTrace();
									Toast.makeText(
											view.getContext(),
											R.string.it_s_not_possible_open_the_camera_,
											Toast.LENGTH_LONG).show();
								}
								break;
							}
							dialog.dismiss();
						}
					});
			levelDialog = builder.create();
			levelDialog.show();
		}

		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
				imgPhoto.setImageBitmap(displayImage());
			} else if (requestCode == GET_PICTURE
					&& resultCode == Activity.RESULT_OK) {
				File mediaStorageDir = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"SymptonManagement");
				File destinationImagePath = new File(mediaStorageDir.getPath()
						+ File.separator + idCheckIn.toString() + ".jpg");

				String caminho = getRealPathFromURI(data.getData());
				File sourceImagePath = new File(caminho);

				try {
					if (sourceImagePath.exists()) {
						FileInputStream fileInputStream = new FileInputStream(
								sourceImagePath);
						FileChannel src = fileInputStream.getChannel();
						FileOutputStream fileOutputStream = new FileOutputStream(
								destinationImagePath);
						FileChannel dst = fileOutputStream.getChannel();
						dst.transferFrom(src, 0, src.size()); // copy the first
																// file to
																// second.....
						fileInputStream.close();
						fileOutputStream.close();
						src.close();
						dst.close();
						imgPhoto.setImageBitmap(displayImage());
					}
				} catch (Exception e) {
					System.out.println("Error :" + e.getMessage());
				}
			}
		}

		public String getRealPathFromURI(Uri contentUri) {
			String res = null;
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(
					contentUri, proj, null, null, null);
			if (cursor.moveToFirst()) {
				;
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				res = cursor.getString(column_index);
			}
			cursor.close();
			return res;
		}

		private static Uri getOutputMediaFileUri(int type) {
			return Uri.fromFile(getOutputMediaFile(type));
		}

		private static File getOutputMediaFile(int type) {
			// To be safe, you should check that the SDCard is mounted
			// using Environment.getExternalStorageState() before doing this.
			File mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"SymptonManagement");

			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Log.d("SymptonManagement", "failed to create directory");
					return null;
				}
			}

			File mediaFile;
			if (type == MEDIA_TYPE_IMAGE) {
				// Set file name
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ idCheckIn.toString() + ".jpg");
			} else if (type == SELECT_FOLDER) {
				// Set file name
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ idCheckIn.toString() + ".jpg");
			} else {
				return null;
			}
			return mediaFile;
		}

		public static int calculateInSampleSize(BitmapFactory.Options options,
				int reqWidth, int reqHeight) {
			// Raw height and width of image
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;

			if (height > reqHeight || width > reqWidth) {

				final int halfHeight = height / 2;
				final int halfWidth = width / 2;

				// Calculate the largest inSampleSize value that is a power of 2
				// and keeps both
				// height and width larger than the requested height and width.
				while ((halfHeight / inSampleSize) > reqHeight
						&& (halfWidth / inSampleSize) > reqWidth) {
					inSampleSize *= 2;
				}
			}

			return inSampleSize;
		}

		public static Bitmap decodeSampledBitmapFromFile(String filename,
				int reqWidth, int reqHeight) {

			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filename, options);

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(filename, options);
		}

		public static Bitmap fixPic(String pathName) {
			return decodeSampledBitmapFromFile(pathName, 100, 100);
		}

		public Bitmap displayImage() {
			Bitmap myBitmap = null;
			try {
				File mediaStorageDirCount = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"SymptonManagement");
				String targetPath = mediaStorageDirCount.getAbsolutePath();

				File targetDirector = new File(targetPath);
				File[] files = targetDirector.listFiles();
				if (files != null) {
					for (File file : files) {
						String names = file.getName();
						if (names.equals(idCheckIn.toString() + ".jpg")) {
							myBitmap = fixPic(file.getAbsolutePath());
							return myBitmap;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return myBitmap;
		}

		public void getCheckInMedicationList() {
			try {
				final CheckInMedicationSvcApi svcCheckInMedication = CheckInMedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(
						new Callable<Collection<CheckInMedication>>() {
							@Override
							public Collection<CheckInMedication> call()
									throws Exception {
								return svcCheckInMedication
										.findCheckinmedicationByCheckin(idCheckIn);
							}
						}, new TaskCallback<Collection<CheckInMedication>>() {
							@Override
							public void success(
									Collection<CheckInMedication> result) {
								colCheckMed = result;
								updateCheckInMedication();
							}

							@Override
							public void error(Exception e) {
								Toast.makeText(getView().getContext(),
										"No CheckInMedication Data",
										Toast.LENGTH_SHORT).show();
							}
						});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void updateCheckInMedication() {
			try {
				final CheckInMedicationSvcApi svcCheckInMedication = CheckInMedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						for (CheckInMedication checkMed : colCheckMed) {
							Long idMedication = checkMed.getMedication();
							int x = 0;
							for (x = 0; x < arrayID.size(); x++) {
								if (arrayID.get(x).equals(
										idMedication.toString())) {
									checkMed.setDate(arrayDate.get(x)
											.toString());
									checkMed.setTime(arrayTime.get(x)
											.toString());
									checkMed.setTakestate(arrayState.get(x)
											.toString());
									svcCheckInMedication
											.setCheckInMedication(checkMed);
								}
							}
						}
						return null;
					}
				}, new TaskCallback<Void>() {
					@Override
					public void success(Void result) {
						getCheckIn();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No Medication Data", Toast.LENGTH_SHORT)
								.show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void getCheckIn() {
			try {
				final CheckInSvcApi svcCheckIn = CheckInSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<CheckIn>() {
					@Override
					public CheckIn call() throws Exception {
						return svcCheckIn.getCheckInById(idCheckIn);
					}
				}, new TaskCallback<CheckIn>() {
					@Override
					public void success(CheckIn result) {
						checkInPub = result;
						updateCheckIn();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No getCheckIn Data", Toast.LENGTH_SHORT)
								.show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void updateCheckIn() {
			try {
				final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				final CheckInSvcApi svcCheckIn = CheckInSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<CheckIn>() {
					@Override
					public CheckIn call() throws Exception {
						checkInPub.setDatecheckin(date);
						checkInPub.setTakemed(takeMeds);
						checkInPub.setPainlevel(eatLevel);
						checkInPub.setThroatlevel(throatLevel);
						checkInPub.setState("1");
						checkInPub.setObservations(etObs.getText().toString());
						return svcCheckIn.setCheckIn(checkInPub);
					}
				}, new TaskCallback<CheckIn>() {
					@Override
					public void success(CheckIn result) {
						if (throatLevel.equals("2") || eatLevel.equals("2")) {
							setAlarmingSymptons();
						} else {
							// new try
							getActivity().moveTaskToBack(true);
							Intent navHome = new Intent(getView().getContext(),
									Authentication.class);
							startActivity(navHome);
						}
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No updateCheckIn Data", Toast.LENGTH_SHORT)
								.show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void setAlarmingSymptons() {
			try {
				final AlarmingSymptonsSvcApi svcAlarming = AlarmingSymptonsSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						String date = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(new Date());
						if (throatLevel.equals("2") && eatLevel.equals("2")) {
							AlarmingSymptons alarm = new AlarmingSymptons(
									"Throat and Eating Alert",
									"Severe and Stop Eating/Drinking", date,
									idCheckIn);
							svcAlarming.addAlarmingSymptons(alarm);
						} else if (throatLevel.equals("2")) {
							AlarmingSymptons alarm = new AlarmingSymptons(
									"Throat Alert", "Severe", date, idCheckIn);
							svcAlarming.addAlarmingSymptons(alarm);
						} else if (eatLevel.equals("2")) {
							AlarmingSymptons alarm = new AlarmingSymptons(
									"Eat/Drink Alert", "Stop Eat/Drink", date,
									idCheckIn);
							svcAlarming.addAlarmingSymptons(alarm);
						}
						return null;
					}
				}, new TaskCallback<Void>() {
					@Override
					public void success(Void result) {
						getActivity().moveTaskToBack(true);
						Intent navHome = new Intent(getView().getContext(),
								Authentication.class);
						startActivity(navHome);
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No setAlarmingSymptons Data",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void getCheckInData() {
			try {
				final CheckInSvcApi svcCheckIn = CheckInSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<CheckIn>() {
					@Override
					public CheckIn call() throws Exception {
						return svcCheckIn.getCheckInById(idCheckIn);
					}
				}, new TaskCallback<CheckIn>() {
					@Override
					public void success(CheckIn result) {
						etObs.setText(result.getObservations());
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No getCheckIn Data", Toast.LENGTH_SHORT)
								.show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void disableTexts() {
			etObs.setEnabled(false);
		}

		public void enableTexts() {
			etObs.setEnabled(true);
		}
	}
}
