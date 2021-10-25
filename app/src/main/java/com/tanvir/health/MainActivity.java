package com.tanvir.health;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.tanvir.health.model.Stories;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ImageView shine;
    CardView startBtnOnMA;
    String catid, stid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            catid = intent.getExtras().getString("catid");
            stid = intent.getExtras().getString("stid");
            if (catid != null) {
                getStory(catid, stid);
            }
        }


        shine = findViewById(R.id.shine);
        startBtnOnMA = findViewById(R.id.startBtnOnMA);

        shineStart();


        startBtnOnMA.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CategoryListActivity.class));
            finish();
        });

        findViewById(R.id.bottomRateUs).setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });
        findViewById(R.id.bottomMoreApps).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Smart+Corporation"))));
        findViewById(R.id.bottomShare).setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Download the app from below link \n https://play.google.com/store/apps/details?id=" + getPackageName();
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shineStart();
                    }
                });
            }
        }, 3, 3, TimeUnit.SECONDS);
    }

    private void getStory(String catid, String stid) {

        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference("categories");
        catRef.keepSynced(true);
        catRef.child(catid).child("stories").child(stid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Stories stories = snapshot.getValue(Stories.class);

                Intent intent = new Intent(MainActivity.this,StoryDetails.class);
                assert stories != null;
                intent.putExtra("title",stories.getTitle());
                intent.putExtra("details",stories.getDetails());
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void shineStart() {

        Animation animation = new TranslateAnimation(0, startBtnOnMA.getWidth() + shine.getWidth(), 0, 0);
        animation.setDuration(560);
        animation.setFillAfter(false);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        shine.startAnimation(animation);
    }


}