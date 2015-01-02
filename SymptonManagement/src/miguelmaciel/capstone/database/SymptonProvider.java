package miguelmaciel.capstone.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class SymptonProvider extends ContentProvider {

	// database
	private DbHelper database;

	private static final String AUTHORITY = "miguelmaciel.capstone.symptonprovider";

	// used for the UriMacher
	private static final int ALARMTIMES_getAlarmTimes = 0;
	private static final int SYSTEMPREFERENCES_getSystemPreferences = 1;

	private static final String PATH_getSystemPreferences = "getSystemPreferences";
	private static final String PATH_getAlarmTimes = "getAlarmTimes";

	public static final Uri CONTENT_URI_getSystemPreferences = Uri
			.parse("content://" + AUTHORITY + "/" + PATH_getSystemPreferences);
	public static final Uri CONTENT_URI_getAlarmTimes = Uri.parse("content://"
			+ AUTHORITY + "/" + PATH_getAlarmTimes);

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		sURIMatcher.addURI(AUTHORITY, PATH_getSystemPreferences,
				SYSTEMPREFERENCES_getSystemPreferences);
		sURIMatcher.addURI(AUTHORITY, PATH_getAlarmTimes,
				ALARMTIMES_getAlarmTimes);
	}

	@Override
	public boolean onCreate() {
		database = new DbHelper(getContext());
		return false;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return Uri.parse("");
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// Cursor
		Cursor cursor = null;

		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case SYSTEMPREFERENCES_getSystemPreferences:
			cursor = database.getSystemPreferences(Long.parseLong(selection));
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			break;
		case ALARMTIMES_getAlarmTimes:
			cursor = database.getAlarmTimes(Long.parseLong(selection));
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		return cursor;
	}
}
