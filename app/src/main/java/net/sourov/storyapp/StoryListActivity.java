package net.sourov.storyapp;

import android.content.Intent;
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

import net.sourov.storyapp.adapter.StAdapter;
import net.sourov.storyapp.model.Stories;

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