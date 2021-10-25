package com.tanvir.health;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.tanvir.health.adapter.StAdapter;
import com.tanvir.health.model.Stories;

import java.util.ArrayList;
import java.util.List;

public class StoryListActivity extends AppCompatActivity {
    List<Stories> stList;
    StAdapter mAdapter;

    RecyclerView recyclerView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        String catid = getIntent().getStringExtra("catid");
        String catName = getIntent().getStringExtra("name");

        toolbar = findViewById(R.id.toolbarOnSL);
        recyclerView = findViewById(R.id.revStories);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        displayStories(catid);

        toolbar.setTitle(catName);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            Intent a = new Intent(getApplicationContext(), OnlyTextActivity.class);

            if (item.getItemId() == R.id.menuAbout) {
                a.putExtra("title", "About");
            } else if (item.getItemId() == R.id.menuInfo) {
                a.putExtra("title", "Info");
            }
            startActivity(a);
            return false;
        });

        initBottomBanner();
    }

    private void initBottomBanner() {
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
    }

    private void displayStories(String catid) {
        stList = new ArrayList<>();

        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference("categories");
        catRef.keepSynced(true);
        catRef.child(catid).child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Stories contacts = ds.getValue(Stories.class);
                    stList.add(contacts);
                    mAdapter = new StAdapter(StoryListActivity.this, stList);
                    recyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StoryListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }





}