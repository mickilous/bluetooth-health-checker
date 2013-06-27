package com.crazzyapps.bluetoothhealthchecker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BluetoothServiceRunner {

	private SharedPreferences	preferences;
	private PendingIntent		btServicePIntent;
	private AlarmManager		alarm;

	private boolean				running	= false;

	public BluetoothServiceRunner(Context context) {

		preferences = PreferenceManager.getDefaultSharedPreferences(context);

		Intent btServiceIntent = new Intent(context, BluetoothService.class);
		btServicePIntent = PendingIntent.getService(context, 0, btServiceIntent, 0);

		alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	public void start() {
		int interval = Integer.parseInt(preferences.getString(C.prefs.TIME_INTERVAL, C.prefs.TIME_INTERVAL_DEFAULT)) * 1000;
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, btServicePIntent);
		running = true;
	}

	public void stop() {
		alarm.cancel(btServicePIntent);
		running = false;
	}

	public void restart() {
		stop();
		start();
	}

	public boolean isRunning() {
		return running;
	}

}
