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
    private static final String TAG = "MainActivity";
    RepoListDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repoList = new ArrayList<>();


        fabAddRepoButton = findViewById(R.id.fab_add_repo);
        repoListCardView = findViewById(R.id.repo_list_card_view);
        labelEmptyListLayout=findViewById(R.id.empty_list_layout);


        //set layout manager to recycler view
        repoListRecyclerView = findViewById(R.id.repo_recyclerview);

        //Access data from database and store in repoList
         dbHelper= new RepoListDBHelper(getApplicationContext());
         repoList = dbHelper.fetchRepo();


        if (repoList.isEmpty()) {
            // The list is empty, so display the "Add Now" button and label
            repoListRecyclerView.setVisibility(View.GONE);
            labelEmptyListLayout.setVisibility(View.VISIBLE);
        } else {
            // The list is not empty, so display the RecyclerView with the repositories
            repoListRecyclerView.setVisibility(View.VISIBLE);
            labelEmptyListLayout.setVisibility(View.GONE);
        }

            //instantiate recyclerviewAdapter
            repoListRecyclerViewAdapter = new RepoListRecyclerViewAdapter(MainActivity.this, repoList);


            repoListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


            fabAddRepoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddRepoActivity.class);
                    startActivity(intent);

                }
            });
        //add adapter to recyclerview
         repoListRecyclerView.setAdapter(repoListRecyclerViewAdapter);
        }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("dataAdded", false)) {
            // Reload data from SQLite database
            repoList = dbHelper.fetchRepo();

            if (repoList.isEmpty()) {
                // The list is empty, so display the "Add Now" button and label
                repoListRecyclerView.setVisibility(View.GONE);
                labelEmptyListLayout.setVisibility(View.VISIBLE);
            } else {
                // The list is not empty, so display the RecyclerView with the repositories
                repoListRecyclerView.setVisibility(View.VISIBLE);
                labelEmptyListLayout.setVisibility(View.GONE);
            }

            // Update RecyclerView adapter with new data
            repoListRecyclerViewAdapter.setData(repoList);
            repoListRecyclerViewAdapter.notifyDataSetChanged();
            repoListRecyclerView.setAdapter(repoListRecyclerViewAdapter);
        }
    }
}