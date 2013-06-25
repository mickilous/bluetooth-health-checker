package com.crazzyapps.bluetoothhealthchecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private BluetoothServiceRunner	btService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btService = new BluetoothServiceRunner(this);

		defineStartButton();
		defineStopButton();
	}

	private void defineStartButton() {

		((Button) findViewById(R.id.button_start)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				btService.start();
			}

		});
	}

	private void defineStopButton() {
		((Button) findViewById(R.id.button_stop)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				btService.stop();
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
