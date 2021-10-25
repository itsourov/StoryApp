package net.sourov.storyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.sourov.storyapp.adapter.CatAdapter;
import net.sourov.storyapp.model.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {


    List<Categories> catList;
    CatAdapter mAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Toolbar toolbar = findViewById(R.id.toolbarOnCL);


        recyclerView = findViewById(R.id.revCat);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        displayCategories();

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

    private void displayCategories() {

        catList = new ArrayList<>();

        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference("categories");
        catRef.keepSynced(true);

        catRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                catList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Categories contacts = ds.getValue(Categories.class);
                    catList.add(contacts);
                    mAdapter = new CatAdapter(CategoryListActivity.this, catList);
                    recyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}