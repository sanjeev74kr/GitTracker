package com.example.gittracker;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RepoListRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView repoName,repoDescription;
    ImageButton shareButton;
    CardView repoCardView;
    public RepoListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        repoName=itemView.findViewById(R.id.repo_name);
        repoDescription=itemView.findViewById(R.id.repo_description);
        shareButton=itemView.findViewById(R.id.share_button);
        repoCardView=itemView.findViewById(R.id.repo_list_card_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
