package com.example.gittracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepoListRecyclerViewAdapter extends RecyclerView.Adapter<RepoListRecyclerViewHolder> {
    List<RepoModel> repoList;
    Context context;
    RepoListRecyclerViewAdapter(Context context,List<RepoModel> repoList)
    {
        super();
        this.repoList=repoList;
        this.context=context;
    }

    @NonNull
    @Override
    public RepoListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.repo_list_recyclerview_layout,parent,false);
        return new RepoListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoListRecyclerViewHolder holder, int position) {
        String repoName=repoList.get(position).repoName;
        String description=repoList.get(position).description;
        String html_url=repoList.get(position).html_url;
        holder.repoName.setText(repoName);


        if(description!=null)
             holder.repoDescription.setText(description);
        else
            holder.repoDescription.setText(R.string.no_description);


            holder.repoCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = repoList.get(holder.getAdapterPosition()).html_url;

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent);

                }
            });

            // Set the onClickListener for the share button

            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String repoName = repoList.get(holder.getAdapterPosition()).repoName;
                    String html_url = repoList.get(holder.getAdapterPosition()).html_url;
                    // Create an Intent with ACTION_SEND action
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    // Set the text to be shared
                    String text = "Check out this repository: " + repoName + " - " + html_url;
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    // Launch the share dialog
                    v.getContext().startActivity(Intent.createChooser(intent, "Share via"));
                }
            });

    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }
}
