package id.daruanugerah.moviecataloguesub4.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.activity.MainActivity;
import id.daruanugerah.moviecataloguesub4.api.ApiClient;
import id.daruanugerah.moviecataloguesub4.api.ApiService;
import id.daruanugerah.moviecataloguesub4.model.Catalog;
import id.daruanugerah.moviecataloguesub4.model.CatalogResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String EXTRA_TYPE = "type";
    private static final String TYPE_DAILY_REMINDER = "daily_reminder";
    private static final String TYPE_RELEASE_REMINDER = "release_reminder";
    private static final int ID_DAILY_REMINDER = 100;
    private static final int ID_RELEASE_TODAY = 101;

    private Context context;

    public NotificationReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type.equals(TYPE_DAILY_REMINDER)) {
            showDailyReminder(context);
        } else if (type.equals(TYPE_RELEASE_REMINDER)) {
            getReleaseToday(context);
        }

    }

    private void getReleaseToday(Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = simpleDateFormat.format(date);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<CatalogResponse> call = apiService.getReleasedCatalog(now, now);

        call.enqueue(new Callback<CatalogResponse>() {
            @Override
            public void onResponse(Call<CatalogResponse> call, Response<CatalogResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Catalog> catalogs = response.body().getResults();
                    int id = 2;
                    for (Catalog catalog : catalogs) {
                        String title = catalog.getTitle();
                        String desc = title + " " + context.getString(R.string.notif_release_today_message);
                        showReleaseToday(context, title, desc, id);
                        id++;
                    }
                }
            }

            @Override
            public void onFailure(Call<CatalogResponse> call, Throwable t) {

            }
        });
    }

    private void showReleaseToday(Context context, String title, String desc, int id) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Today_release_channel";

        Intent showReleaseTodayIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, showReleaseTodayIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_movie))
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }

    private void showDailyReminder(Context context) {
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "channel_1";
        String CHANNEL_NAME = "Daily_Reminder_Channel";

        Intent showDailyReminderIntet = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, showDailyReminderIntet, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_movie))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.notif_daily_reminder_message))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    public void setReleaseTodayReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY, getReminderIntent(TYPE_RELEASE_REMINDER), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_RELEASE_REMINDER).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private Calendar getReminderTime(String typeReleaseReminder) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, typeReleaseReminder.equals(TYPE_DAILY_REMINDER) ? 7 : 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar;
    }

    private Intent getReminderIntent(String typeReleaseReminder) {
        Intent getReminderIntentIntent = new Intent(context, NotificationReceiver.class);
        getReminderIntentIntent.putExtra(EXTRA_TYPE, typeReleaseReminder);
        return getReminderIntentIntent;
    }

    public void cancelReleaseToday(Context applicationContext) {
        cancelReminder(applicationContext, TYPE_RELEASE_REMINDER);
    }

    private void cancelReminder(Context context, String typeReleaseReminder) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent cancelReminderIntent = new Intent(context, NotificationReceiver.class);
        int requestCode = typeReleaseReminder.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASE_TODAY;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, cancelReminderIntent, 0);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void setDailyReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY_REMINDER), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_DAILY_REMINDER).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public void cancelDailyReminder(Context applicationContext) {
        cancelReminder(applicationContext, TYPE_DAILY_REMINDER);
    }
}
