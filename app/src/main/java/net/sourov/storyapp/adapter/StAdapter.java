package net.sourov.storyapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

import net.sourov.storyapp.R;
import net.sourov.storyapp.StoryDetails;
import net.sourov.storyapp.model.Stories;

import java.util.List;

public class StAdapter extends RecyclerView.Adapter<StAdapter.stHolder>{


    Context context;
    List<Stories> stList;


    public StAdapter(Context context, List<Stories> stList) {
        this.context = context;
        this.stList = stList;
    }


    @NonNull
    @NotNull
    @Override
    public stHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_item, parent,false);
        return new stHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull stHolder holder, int position) {


        Stories stories = stList.get(position);

        holder.textViewOnStoryItem.setText(stories.getTitle());

        holder.cardVOnStoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, StoryDetails.class);
                intent.putExtra("title",stories.getTitle());
                intent.putExtra("details",stories.getDetails());
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return stList.size();
    }

    class stHolder extends RecyclerView.ViewHolder{

       TextView textViewOnStoryItem;
       CardView cardVOnStoryItem;

        public stHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cardVOnStoryItem = itemView.findViewById(R.id.cardVOnStoryItem);
            textViewOnStoryItem = itemView.findViewById(R.id.textViewOnStoryItem);

        }
    }
}