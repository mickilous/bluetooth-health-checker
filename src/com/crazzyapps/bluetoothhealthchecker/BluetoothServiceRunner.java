package com.crazzyapps.bluetoothhealthchecker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class BluetoothServiceRunner {

	private PendingIntent	btServicePIntent;
	private AlarmManager	alarm;

	public BluetoothServiceRunner(Context context) {
		Intent btServiceIntent = new Intent(context, BluetoothService.class);
		btServicePIntent = PendingIntent.getService(context, 0, btServiceIntent, 0);
		alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	public void start() {
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000, btServicePIntent);
	}

	public void stop() {
		alarm.cancel(btServicePIntent);
	}

}
