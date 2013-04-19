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

	private void trace(String message) {

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " + message);
		// Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}
