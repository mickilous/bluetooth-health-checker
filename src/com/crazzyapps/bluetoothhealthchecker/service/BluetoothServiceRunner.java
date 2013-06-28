package com.crazzyapps.bluetoothhealthchecker.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.crazzyapps.bluetoothhealthchecker.C;
import com.crazzyapps.bluetoothhealthchecker.C.prefs;
import com.google.inject.Inject;

public class BluetoothServiceRunner {

	@Inject
	SharedPreferences		preferences;

	@Inject
	private AlarmManager	alarm;

	private PendingIntent	btServicePIntent;

	private boolean			running	= false;

	@Inject
	public BluetoothServiceRunner(Context context) {
		Intent btServiceIntent = new Intent(context, BluetoothService.class);
		btServicePIntent = PendingIntent.getService(context, 0, btServiceIntent, 0);
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
