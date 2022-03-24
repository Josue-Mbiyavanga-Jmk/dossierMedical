package com.example.dossier_medical.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.dossier_medical.Memory.Keys;
import com.example.dossier_medical.Memory.Preferences;
import com.example.dossier_medical.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class ActivityFlashScream extends AppCompatActivity {

    public static final int MULTIPLE_PERMISSIONS = 10;

    private customfonts.MyTextView_Roboto_Regular BtNext;
    private SpinKitView progress_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String conf = Preferences.get(Keys.PreferencesKeys.CONFIG_IP);
        if(conf==null)
        {
            Preferences.save(Keys.PreferencesKeys.CONFIG_IP,"192.168.43.103");
            // Preferences.save(Keys.PreferencesKeys.CONFIG_IP,"192.168.43.47");
            // Preferences.save(Keys.PreferencesKeys.CONFIG_IP,"192.168.43.224");
            // Preferences.save(Keys.PreferencesKeys.CONFIG_IP,"192.168.1.132");
            Preferences.save(Keys.PreferencesKeys.CONFIG_PORT,"8091");
        }


        //Preferences.save(Keys.PreferencesKeys.CONFIG_IP,"192.168.43.1");
        //  Preferences.save(Keys.PreferencesKeys.CONFIG_IP,"192.168.43.224");

        String date_inf = Preferences.get(Keys.PreferencesKeys.TIME_STAMP_REF);
        if(date_inf==null)
        {
            Preferences.save(Keys.PreferencesKeys.TIME_STAMP_REF,"2000-03-30 12:05:08.347075");
        }

        initialiseWidget();
        event();
        //les permissions
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.VIBRATE,
                Manifest.permission.CALL_PHONE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }

        }).check();

    }

    private void initialiseWidget()
    {
        BtNext =findViewById(R.id.BtNext);
        progress_load =findViewById(R.id.spin_kit_load);
    }

    private void event()
    {
        BtNext .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_load.setVisibility(View.VISIBLE);
                //
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Write whatever to want to do after delay specified (1 sec)
                        progress_load.setVisibility(View.GONE);
                        Intent signin = new Intent(ActivityFlashScream.this, ActivityLogin.class);
                        startActivity(signin);
                        finish();
                    }
                }, 3000);
            }
        });
    }
}