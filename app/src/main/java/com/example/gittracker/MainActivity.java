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
        RepoListDBHelper dbHelper = new RepoListDBHelper(getApplicationContext());
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

            //add adapter to recyclerview
            repoListRecyclerView.setAdapter(repoListRecyclerViewAdapter);

            repoListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


            fabAddRepoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddRepoActivity.class);
                    startActivity(intent);

                }
            });

            Log.d(TAG, "onCreate() called");
        }


    @Override
        protected void onStart() {
            super.onStart();
            Log.d(TAG, "onStart() called");
        }

        @Override
        protected void onResume() {
            super.onResume();

            Log.d(TAG, "onResume() called");
        }


        @Override
        public void onRestart()
        {
            super.onRestart();
          //  repoListRecyclerViewAdapter.notifyItemInserted(repoList.size()-1);
            Log.d(TAG,"onRestart called");
        }


}