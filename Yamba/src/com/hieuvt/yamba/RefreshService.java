package com.hieuvt.yamba;

import java.util.ArrayList;
import java.util.List;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class RefreshService extends IntentService {

	static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "onCreated");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		// SharedPreferences prefs = PreferenceManager
		// .getDefaultSharedPreferences(this);
		// String username = prefs.getString("username", "");
		// String password = prefs.getString("password", "");
		//
		// if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
		// Toast.makeText(this, "Please update your username and password",
		// Toast.LENGTH_LONG).show();
		// return;
		// }

		int count = 0;
		List<MyStatus> timeline = StatusContract.timeline;

		/*
		 * YambaClient cloud = new YambaClient(username, password);
		 * 
		 * List<Status> timeline = new ArrayList<Status>();
		 * 
		 * try { timeline = cloud.getTimeline(20); for (Status status :
		 * timeline) { Log.d(TAG, String.format("%s: %s", status.getUser(),
		 * status.getMessage())); } } catch (YambaClientException e) { // TODO
		 * Auto-generated catch block Log.e(TAG, "Failed to fetch the timeline",
		 * e); e.printStackTrace(); }
		 */

		Log.d(TAG, "onStarted");

		DbHelper dbHelper = new DbHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();

		// db.execSQL("drop table if exists " + StatusContract.TABLE);
		for (MyStatus status : timeline) {
			values.clear();
			// values.put(StatusContract.Column.ID, status.getId());
			values.put(StatusContract.Column.USER, status.getUser());
			values.put(StatusContract.Column.MESSAGE, status.getMessage());
			values.put(StatusContract.Column.CREATED_AT, status.getCreateAt());

			// db.insertWithOnConflict(StatusContract.TABLE, null, values,
			// SQLiteDatabase.CONFLICT_IGNORE);

			Uri uri = getContentResolver().insert(StatusContract.CONTENT_URI,
					values);
			if (uri != null) {
				count++;
				Log.d(TAG,
						String.format("%s: %s", status.getUser(),
								status.getMessage()));
			}

		}
		if (count > 0) {
			 sendBroadcast(new Intent("com.hieuvt.yamba.action.NEW_STATUSES")
			 .putExtra("count", count));
			 Log.d(TAG, "Broadcast send");

		}
		return;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}
}
