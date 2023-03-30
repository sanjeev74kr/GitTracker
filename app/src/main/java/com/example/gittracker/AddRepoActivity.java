package com.example.gittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;

public class AddRepoActivity extends AppCompatActivity {
 TextInputEditText ownerField,repoField;
 Button addBtn;

 List<RepoModel> repoList;

 String ownerName="",repoName="",description="",html_url="";

 ConsumeApi consumeApi;

 private static final String TAG = "AddRepoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repo);

        ownerField = findViewById(R.id.edit_text_owner_name);
        repoField = findViewById(R.id.edit_text_repo_name);
        addBtn = findViewById(R.id.add_repo_btn);

        repoList = new ArrayList<>();

        consumeApi = new ConsumeApi();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ownerName = ownerField.getText().toString();
                repoName = repoField.getText().toString();

                Log.d(TAG, "Owner: " + ownerName + " repoName: " + repoName);

                if(!ownerName.isEmpty() && !repoName.isEmpty()) {
                    consumeApi.fetchGithubRepo(ownerName, repoName, new ConsumeApi.Callback() {
                        @Override
                        public void onSuccess(String responseBody) {
                            try {
                                Gson gson = new Gson();
                                RepoModel repoModel = gson.fromJson(responseBody, RepoModel.class);
                                Log.d(TAG,"repoModel:"+repoModel.html_url);

                                description = repoModel.description;
                                html_url = repoModel.html_url;


                                if(repoModel.html_url!=null) {
                                    RepoListDBHelper dbHelper = new RepoListDBHelper(getApplicationContext());
                                    dbHelper.addRepo(ownerName, repoName, description, html_url);

//                                    int position = repoList.size(); // get the position where new item is inserted
//                                    repoList.add(repoModel)
                                    new RepoListRecyclerViewAdapter(getApplicationContext(),repoList).notifyItemInserted(repoList.size()-1);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            Toast.makeText(getApplicationContext(), "Your data saved successfully", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                                else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "No such repo or owner is available on github", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } catch (JsonParseException e) {
                                Log.e(TAG, "Error parsing JSON response: " + e.getMessage());

                            } catch (Exception e) {
                                Log.e(TAG, "Error adding repo to database: " + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e(TAG, "Error fetching repo from API: " + e.getMessage());
                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(),"Please enter above data to save",Toast.LENGTH_SHORT).show();
            }
        });
    }
}