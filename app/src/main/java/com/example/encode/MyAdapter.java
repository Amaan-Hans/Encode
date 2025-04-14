package com.example.encode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MyItem> items;

    public MyAdapter(List<MyItem> items) {
        this.items = items;
    }

    // Create ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView questionTextView;
        public ImageButton commentButton;
        public TextView likeCountTextView;
        public ImageButton likeButton;

        public TextView question_id;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username);
            questionTextView = itemView.findViewById(R.id.question_card);
            commentButton = itemView.findViewById(R.id.comment_button);
            likeCountTextView = itemView.findViewById(R.id.likecount);
            likeButton = itemView.findViewById(R.id.like_button);
            question_id = itemView.findViewById(R.id.question_id);
        }
    }

    // Create ViewHolder instances
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new ViewHolder(view);
    }

    // Bind data to ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyItem item = items.get(position);
        holder.usernameTextView.setText(item.getUsername());
        holder.questionTextView.setText(item.getQuestion());
        holder.likeCountTextView.setText(String.valueOf(item.getLikeCount()));
        holder.question_id.setText(String.valueOf(item.getid()));
        // Set click listeners or perform other actions as needed
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
