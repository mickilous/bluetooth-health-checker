package com.crazzyapps.bluetoothhealthchecker;

import roboguice.service.RoboIntentService;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.SystemClock;

import com.google.inject.Inject;

public class BluetoothService extends RoboIntentService {

	@Inject
	NotifManager	notifier;

	public BluetoothService() {
		super("BluetoothService");
	}

	@Override
	public void onCreate() {
		trace("Creating Service");
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		trace("Starting Service");
		testBluettooth();
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
			notifier.notifyBluetoothUnsupported();
		} else if (!bluetooth.isEnabled() && BluetoothAdapter.STATE_OFF == bluetooth.getState()) {
			// createNotification();
			trace("Testing Bluetooth Health");
			bluetooth.enable();
			SystemClock.sleep(1000);

			boolean status = bluetooth.isEnabled();
			trace("Enabled : " + status);

			bluetooth.disable();
			notifier.notifyBluetoothStatus(status);

		} else {
			trace("Bluetooth is Enable -> No test");
			notifier.notifyBluetoothStatus(true);
		}
	}

	private void trace(String message) {

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " + message);
		// Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}
