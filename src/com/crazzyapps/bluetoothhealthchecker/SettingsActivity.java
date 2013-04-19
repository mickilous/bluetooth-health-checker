package com.crazzyapps.bluetoothhealthchecker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsActivity extends Activity {

	PendingIntent	pintent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		defineStartButton();
		defineStopButton();
	}

	private void defineStartButton() {

		((Button) findViewById(R.id.button_start)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this, BluetoothService.class);
				pintent = PendingIntent.getService(SettingsActivity.this, 1, intent, 0);

				AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				// Start every 5 seconds
				alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000, pintent);
			}

		});
	}

	private void defineStopButton() {
		((Button) findViewById(R.id.button_stop)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

				alarm.cancel(pintent);
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
