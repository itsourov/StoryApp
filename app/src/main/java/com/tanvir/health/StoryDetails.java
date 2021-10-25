package com.tanvir.health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
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
}