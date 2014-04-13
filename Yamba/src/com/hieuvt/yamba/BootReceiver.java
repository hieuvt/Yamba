package com.hieuvt.yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	private static final String TAG = BootReceiver.class.getSimpleName();
	private static final long DEFAULT_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		long interval = 5 * 60 * 1000;
		PendingIntent operation = PendingIntent.getService(context, -1,
				new Intent(context, RefreshService.class),
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC,
				System.currentTimeMillis(), interval, operation);
		context.startService(new Intent(context, RefreshService.class));
		Log.d(TAG, "setting repeat operation for: " + interval);
		Log.d(TAG, "onReceived");
		
	}

}
