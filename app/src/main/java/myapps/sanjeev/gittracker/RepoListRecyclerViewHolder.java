package myapps.sanjeev.gittracker;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RepoListRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView repoName,repoDescription;
    ImageButton shareButton,deleteButton;
    CardView repoCardView;

    public RepoListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        repoName=itemView.findViewById(R.id.repo_name);
        repoDescription=itemView.findViewById(R.id.repo_description);
        shareButton=itemView.findViewById(R.id.share_button);
        deleteButton=itemView.findViewById(R.id.delete_button);
        repoCardView=itemView.findViewById(R.id.repo_list_card_view);
    }
}
