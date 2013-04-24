package com.crazzyapps.bluetoothhealthchecker;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;

public class BluetoothService extends IntentService {

	private static final int	NOTIFICATION	= 0;
	private SharedPreferences	preferences;

	public BluetoothService() {
		super("BluetoothService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		trace("Starting Service");
		testBluettooth();
	}

	@Override
	public void onCreate() {
		trace("Creating Service");

		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		trace("Stopping Service");
		super.onDestroy();
	}

	private void testBluettooth() {
		trace("Testing Bluetooth");
		BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
		if (bluetooth == null) {
			trace("Device does not support Bluetooth");
			notifyBluetoothUnsupported();
		} else if (!bluetooth.isEnabled()) {
			// createNotification();
			trace("Testing Bluetooth Health");
			bluetooth.enable();
			SystemClock.sleep(1000);

			boolean status = bluetooth.isEnabled();
			trace("Enabled : " + status);

			bluetooth.disable();
			notifyBluetoothStatus(status);

		} else {
			trace("Bluetooth is Enable -> No test");
			notifyBluetoothStatus(true);
		}
	}

	private void notifyBluetoothStatus(boolean status) {
		if (status)
			notifyHealthy();
		else
			notifySick();

	}

	private Builder defineNotification() {

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(SettingsActivity.class);
		stackBuilder.addNextIntent(new Intent(this, SettingsActivity.class));
		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		Builder notifBuilder = new NotificationCompat.Builder(this)
				.setContentTitle(getResources().getString(R.string.app_name)).setAutoCancel(true)
				.setContentIntent(pendingIntent);

		return notifBuilder;
	}

	private void notifyHealthy() {
		if (preferences.getBoolean(C.prefs.NOTIF_WHEN_HEALTHY, false)) {
			notify(R.string.notification_ok, R.drawable.ic_launcher, NotificationCompat.PRIORITY_DEFAULT);
		}
	}

	private void notifySick() {
		notify(R.string.notification_ko, R.drawable.notif_ko, NotificationCompat.PRIORITY_MAX);
		if (preferences.getBoolean(C.prefs.VIBRATE_WHEN_KO, false))
			vibrate();
		if (preferences.getBoolean(C.prefs.SOUND_WHEN_KO, false))
			sound();
	}

	private void notifyBluetoothUnsupported() {
		notify(R.string.notification_notsuppored, R.drawable.notif_ko, NotificationCompat.PRIORITY_HIGH);
	}

	private void notify(int contentText, int smallIcon, int priority) {
		Builder notifBuilder = defineNotification();
		notifBuilder.setContentText(getResources().getString(contentText)).setSmallIcon(smallIcon)
				.setPriority(priority);
		((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(NOTIFICATION,
				notifBuilder.build());
	}

	private void vibrate() {
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(100);
	}

	private void sound() {
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone ringtoneManager = RingtoneManager.getRingtone(getApplicationContext(), notification);
		ringtoneManager.play();
	}

	private void trace(String message) {

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " + message);
		// Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}
