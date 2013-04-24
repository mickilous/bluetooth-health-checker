package com.crazzyapps.bluetoothhealthchecker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	PendingIntent	btServicePIntent;
	AlarmManager	alarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		defineIntents();
		defineSystemServices();

		defineStartButton();
		defineStopButton();
	}

	private void defineSystemServices() {
		alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	}

	private void defineIntents() {
		Intent btServiceIntent = new Intent(MainActivity.this, BluetoothService.class);
		btServicePIntent = PendingIntent.getService(MainActivity.this, 0, btServiceIntent, 0);
	}

	private void defineStartButton() {

		((Button) findViewById(R.id.button_start)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// Start every 5 seconds
				alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000, btServicePIntent);
			}

		});
	}

	private void defineStopButton() {
		((Button) findViewById(R.id.button_stop)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				alarm.cancel(btServicePIntent);
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_main) {
			startActivityForResult(new Intent(this, SettingsActivity.class), 0);
		}
		return super.onOptionsItemSelected(item);
	}

}
