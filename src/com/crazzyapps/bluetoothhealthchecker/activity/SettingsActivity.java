package com.crazzyapps.bluetoothhealthchecker.activity;

import roboguice.activity.RoboPreferenceActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.crazzyapps.bluetoothhealthchecker.C;
import com.crazzyapps.bluetoothhealthchecker.R;
import com.crazzyapps.bluetoothhealthchecker.C.prefs;
import com.crazzyapps.bluetoothhealthchecker.R.xml;
import com.crazzyapps.bluetoothhealthchecker.service.BluetoothServiceRunner;
import com.google.inject.Inject;

public class SettingsActivity extends RoboPreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {

	@Inject
	BluetoothServiceRunner	btService;
	@Inject
	SharedPreferences		preferences;

	@Override
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		preferences.registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		preferences.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (C.prefs.SERVICE_ENABLED.equals(key))
			enableService(prefs.getBoolean(key, false));
		if (C.prefs.TIME_INTERVAL.equals(key))
			changeTimeInterval();

	}

	private void changeTimeInterval() {
		toast("TODO MODIFY IN MINUTES INSTEAD OF SECONDS");
		if (btService.isRunning())
			btService.restart();
	}

	private void enableService(boolean enabled) {
		if (enabled)
			btService.start();
		else
			btService.stop();

	}

	private void toast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

}
