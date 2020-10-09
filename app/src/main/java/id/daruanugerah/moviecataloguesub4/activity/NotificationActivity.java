package id.daruanugerah.moviecataloguesub4.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.daruanugerah.moviecataloguesub4.BuildConfig;
import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.reminder.NotificationReceiver;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.release_switch)
    Switch releaseSwitch;
    @BindView(R.id.daily_switch)
    Switch dailySwitch;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private NotificationReceiver notificationReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(BuildConfig.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        notificationReceiver = new NotificationReceiver(this);

        releaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putBoolean("release_reminder", isChecked);
                sharedPreferencesEditor.apply();

                if (isChecked) {
                    notificationReceiver.setReleaseTodayReminder();
                } else {
                    notificationReceiver.cancelReleaseToday(getApplicationContext());
                }

            }
        });

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putBoolean("daily_reminder", isChecked);
                sharedPreferencesEditor.apply();

                if (isChecked) {
                    notificationReceiver.setDailyReminder();
                } else {
                    notificationReceiver.cancelDailyReminder(getApplicationContext());
                }

            }
        });

        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);
        boolean releaseReminder = sharedPreferences.getBoolean("release_reminder", false);

        dailySwitch.setChecked(dailyReminder);
        releaseSwitch.setChecked(releaseReminder);
    }
}
