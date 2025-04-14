package com.example.encode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<commentitem> items;

    public CommentAdapter(List<commentitem> items) {
        this.items = items;
    }

    // Create ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView questionTextView;
        public TextView likeCountTextView;
        public TextView commentidView;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.commentor_username);
            questionTextView = itemView.findViewById(R.id.commentor_question_card);
            likeCountTextView = itemView.findViewById(R.id.commentor_likecount);
            commentidView =  itemView.findViewById(R.id.comment_id);
        }
    }

    // Create ViewHolder instances
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    // Bind data to ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        commentitem item = items.get(position);
        holder.usernameTextView.setText(item.getUsername());
        holder.questionTextView.setText(item.getQuestion());
        holder.likeCountTextView.setText(String.valueOf(item.getLikeCount()));
        holder.commentidView.setText(String.valueOf(item.getCommentid()));
        // Set click listeners or perform other actions as needed
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
