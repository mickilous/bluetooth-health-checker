package com.crazzyapps.bluetoothhealthchecker;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class BluetoothService extends IntentService {

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
			trace("Testing Bluetooth Health");
			boolean result = mBluetoothAdapter.enable();
			trace("Enabling result : " + result);
			mBluetoothAdapter.disable();
		} else {
			trace("Bluetooth is Enable -> No test");
		}
	}

	// private final void createNotification(){
	// final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	//
	// final Intent launchNotifiactionIntent = new Intent(this, BluetoothService.class);
	// final PendingIntent pendingIntent = PendingIntent.getActivity(this,
	// REQUEST_CODE, launchNotifiactionIntent,
	// PendingIntent.FLAG_ONE_SHOT);
	//
	// Notification.Builder builder = new Notification.Builder(this)
	// .setWhen(System.currentTimeMillis())
	// .setTicker(notificationTitle)
	// .setSmallIcon(R.drawable.notification)
	// .setContentTitle(getResources().getString(R.string.notification_title))
	// .setContentText(getResources().getString(R.string.notification_desc))
	// .setContentIntent(pendingIntent);
	//
	// mNotification.notify(NOTIFICATION_ID, builder.build());
	// }

	private void trace(String message) {

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " + message);
		// Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}
