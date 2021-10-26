package com.tanvir.health.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.annotations.NotNull;

import com.tanvir.health.R;
import com.tanvir.health.StoryListActivity;
import com.tanvir.health.model.Categories;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.catHolder>{


    Context context;
    List<Categories> catList;


    public CatAdapter(Context context, List<Categories> catList) {
        this.context = context;
        this.catList = catList;
    }


    @NonNull
    @NotNull
    @Override
    public catHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_item, parent,false);
        return new catHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull catHolder holder, int position) {


        Categories contacts = catList.get(position);

        Glide.with(context).load(contacts.getCatimage()).into(holder.imageViewOnCatItem);
        holder.cardVOnCatItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, StoryListActivity.class);
                intent.putExtra("catid",contacts.getCatid());
                intent.putExtra("name",contacts.getName());
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    class catHolder extends RecyclerView.ViewHolder{

     ImageView imageViewOnCatItem;
     CardView cardVOnCatItem;

        public catHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageViewOnCatItem = itemView.findViewById(R.id.imageViewOnCatItem);
            cardVOnCatItem = itemView.findViewById(R.id.cardVOnCatItem);

        }
    }
}