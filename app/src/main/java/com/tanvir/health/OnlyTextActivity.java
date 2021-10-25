package com.tanvir.health;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlyTextActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_text);

         textView = findViewById(R.id.detailsInOT);
        Toolbar toolbar = findViewById(R.id.toolbarOnOT);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.menuAbout) {
                getData("About");
                toolbar.setTitle("About");
            } else if (item.getItemId() == R.id.menuInfo) {
                getData("Info");
                toolbar.setTitle("Info");
            }
            return false;
        });

        getData(getIntent().getStringExtra("title"));
    }

    private void getData(String title) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("appinfo");
        reference.keepSynced(true);
        reference.child("pages").child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView.setText((CharSequence) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}