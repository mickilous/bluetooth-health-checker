package com.crazzyapps.bluetoothhealthchecker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;

import com.google.inject.Inject;

public class NotifManager {

	private static final int	NOTIFICATION	= 0;

	@Inject
	SharedPreferences			preferences;

	private Context				context;

	@Inject
	public NotifManager(Context context) {
		this.context = context;
		defineNotification();
	}

	public void notifyBluetoothStatus(boolean healthy) {
		if (healthy)
			notifyHealthy();
		else
			notifySick();
	}

	public void notifyBluetoothUnsupported() {
		notify(R.string.notification_notsuppored, R.drawable.notif_ko, NotificationCompat.PRIORITY_HIGH, true);
	}

	private void notifyHealthy() {
		if (preferences.getBoolean(C.prefs.NOTIF_WHEN_HEALTHY, false)) {
			notify(R.string.notification_ok, R.drawable.notif_ok, NotificationCompat.PRIORITY_DEFAULT, true);
		}
	}

	private void notifySick() {
		notify(R.string.notification_ko, R.drawable.notif_ko, NotificationCompat.PRIORITY_MAX, true);
		if (preferences.getBoolean(C.prefs.VIBRATE_WHEN_KO, false))
			vibrate();
		if (preferences.getBoolean(C.prefs.SOUND_WHEN_KO, false))
			sound();
	}

	private void notify(int contentText, int smallIcon, int priority, boolean autoCancel) {
		Builder notifBuilder = defineNotification();
		notifBuilder.setContentText(context.getResources().getString(contentText)).setSmallIcon(smallIcon)
				.setPriority(priority).setAutoCancel(autoCancel);
		((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(NOTIFICATION,
				notifBuilder.build());
	}

	private void vibrate() {
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);
	}

	private void sound() {
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone ringtoneManager = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
		ringtoneManager.play();
	}

	private Builder defineNotification() {

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(SettingsActivity.class);
		stackBuilder.addNextIntent(new Intent(context, SettingsActivity.class));
		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		Builder notifBuilder = new NotificationCompat.Builder(context).setContentTitle(
				context.getResources().getString(R.string.app_name)).setContentIntent(pendingIntent);

		return notifBuilder;
	}

}
