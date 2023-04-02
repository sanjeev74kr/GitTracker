package com.example.gittracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView repoListRecyclerView;
    RepoListRecyclerViewAdapter repoListRecyclerViewAdapter;
    List<RepoModel> repoList;
    FloatingActionButton fabAddRepoButton;
    CardView repoListCardView;
    ConstraintLayout labelEmptyListLayout;
    RepoListDBHelper dbHelper;
    private boolean isDataAdded=false;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repoList = new ArrayList<>();


        fabAddRepoButton = findViewById(R.id.fab_add_repo);
        repoListCardView = findViewById(R.id.repo_list_card_view);
        labelEmptyListLayout=findViewById(R.id.empty_list_layout);
        repoListRecyclerView = findViewById(R.id.repo_recyclerview);

        //Access data from database and store in repoList
         dbHelper= new RepoListDBHelper(getApplicationContext());
         repoList = dbHelper.fetchRepo();

         for(int i=0;i<repoList.size();i++)
             Log.d(TAG, "ownerName:"+repoList.get(i).ownerName+
                     " repoName:"+repoList.get(i).repoName+
                     " description:"+repoList.get(i).description+
                     " html_url:"+repoList.get(i).html_url+
                     " size:"+repoList.size());

         //check list and change UI
          checkIfEmpty(repoList);

            fabAddRepoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddRepoActivity.class);
                    startActivityForResult(intent,1);

                }
            });

        repoListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //instantiate recyclerviewAdapter
        repoListRecyclerViewAdapter = new RepoListRecyclerViewAdapter(MainActivity.this, repoList);
        Log.d(TAG,"repoListRecyclerViewAdapter: "+repoListRecyclerViewAdapter);

        //add adapter to recyclerview
         repoListRecyclerView.setAdapter(repoListRecyclerViewAdapter);

         Log.d(TAG, "onCreate called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            isDataAdded = data.getBooleanExtra("isDataAdded", false);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart called");

        if(isDataAdded) {
            repoList = dbHelper.fetchRepo();
            repoListRecyclerViewAdapter.updateData(repoList);
            repoListRecyclerViewAdapter.notifyItemInserted(repoList.size()-1);

            for (int i = 0; i < repoList.size(); i++)
                Log.d(TAG, "ownerName:" + repoList.get(i).ownerName +
                        " repoName:" + repoList.get(i).repoName +
                        " description:" + repoList.get(i).description +
                        " html_url:" + repoList.get(i).html_url +
                        " size:" + repoList.size());

            repoListRecyclerView.scrollToPosition(repoList.size()-1);

            isDataAdded=false;
        }
        //check list and change UI
        checkIfEmpty(repoList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume Called");
        checkIfEmpty(repoList);
    }

    public void checkIfEmpty(List<RepoModel> repoList)
    {
        if (repoList.isEmpty()) {
            // The list is empty, so display the "Add Now" button and label
            repoListRecyclerView.setVisibility(View.GONE);
            labelEmptyListLayout.setVisibility(View.VISIBLE);
            Log.d(TAG, "checked empty");
        } else {
            // The list is not empty, so display the RecyclerView with the repositories
            repoListRecyclerView.setVisibility(View.VISIBLE);
            labelEmptyListLayout.setVisibility(View.GONE);
            Log.d(TAG, "checked not empty");
        }
    }
}