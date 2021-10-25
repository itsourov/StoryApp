package net.sourov.storyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

public class StoryDetails extends AppCompatActivity {
    TextView titleInSD, detailsInSD;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);

        titleInSD = findViewById(R.id.titleInSD);
        detailsInSD = findViewById(R.id.detailsInSD);
        toolbar = findViewById(R.id.toolbarOnSD);

        titleInSD.setPaintFlags(titleInSD.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        titleInSD.setText(getIntent().getStringExtra("title"));
        detailsInSD.setText(getIntent().getStringExtra("details"));

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
}