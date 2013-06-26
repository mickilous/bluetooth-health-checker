package com.crazzyapps.bluetoothhealthchecker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	private BluetoothServiceRunner	btService;
	private SharedPreferences		preferences;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		btService = new BluetoothServiceRunner(this);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (C.prefs.SERVICE_ENABLED.equals(key))
			enableService(prefs.getBoolean(key, false));
		if (C.prefs.RUN_AT_STARTUP.equals(key))
			runServiceAtStartup(prefs.getBoolean(key, true));
		if (C.prefs.TIME_INTERVAL.equals(key))
			changeTimeInterval(Integer.parseInt(prefs.getString(key, C.prefs.TIME_INTERVAL_DEFAULT)));

	}

	private void changeTimeInterval(int interval) {
		toast("TODO MODIFY IN MINUTES INSTEAD OF SECONDS");
		restartService();
	}

	private void restartService() {
		enableService(false);
		enableService(true);
	}

	private void runServiceAtStartup(boolean boolean1) {
		toast("TODO IMPLEMENT RUN SERVICE @ STARTUP");
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
