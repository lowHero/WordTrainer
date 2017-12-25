package com.nz2dev.wordtrainer.app.services.verifying;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by nz2Dev on 24.12.2017
 */
public class VerifyingService extends IntentService {

    public VerifyingService() {
        super(VerifyingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences preferences = getSharedPreferences("NAME", MODE_PRIVATE);
        preferences.edit()
                .putInt("KEY", 1)
                .apply();

        Toast.makeText(this, "From Service: set value", Toast.LENGTH_SHORT).show();
    }
}
