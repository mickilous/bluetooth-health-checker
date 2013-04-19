package com.crazzyapps.bluetoothhealthchecker;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class BluetoothService extends IntentService {

	private static final int			NOTIFICATION	= 0;
	private NotificationCompat.Builder	notifBuilder;
	private NotificationManager			notificationManager;

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
		defineNotification();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		trace("Stopping Service");
		super.onDestroy();
	}

	private void testBluettooth() {
		trace("Testing Bluetooth");
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			trace("Device does not support Bluetooth");
		} else if (!mBluetoothAdapter.isEnabled()) {
			// createNotification();
			trace("Testing Bluetooth Health");
			boolean status = mBluetoothAdapter.enable();
			trace("Enabling result : " + status);
			notifyBluetoothStatus(status);
			mBluetoothAdapter.disable();
		} else {
			trace("Bluetooth is Enable -> No test");
		}
	}

	private void notifyBluetoothStatus(boolean status) {
		if (status)
			notifyHealthy();
		else
			notifySick();

	}

	private void notifyHealthy() {
		notifBuilder.setContentText("Ca marche toujours bien");
		notificationManager.notify(NOTIFICATION, notifBuilder.build());
	}

	private void notifySick() {
		notifBuilder.setContentText("C'est cassé !!!!!").setSmallIcon(R.drawable.notif_ko);
		notificationManager.notify(NOTIFICATION, notifBuilder.build());
	}

	private void defineNotification() {
		notifBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(getResources().getString(R.string.notification_title))
				.setContentText(getResources().getString(R.string.notification_desc)).setAutoCancel(true);

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, SettingsActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(SettingsActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		notifBuilder.setContentIntent(resultPendingIntent);

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		notificationManager.notify(NOTIFICATION, notifBuilder.build());

	}

	private void trace(String message) {

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " + message);
		// Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}
