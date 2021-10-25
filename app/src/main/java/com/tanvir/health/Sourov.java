package com.tanvir.health;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Sourov extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}