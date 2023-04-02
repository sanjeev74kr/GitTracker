package com.example.gittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RepoListDBHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME="RepoListDB";
  private static final int DATABASE_VERSION=6;
  private static  final String TABLE_NAME="githubRepo";

  //DECLARE TABLE- COLUMN NAME
  private static  final String KEY_ID="id";
  private static final String OWNER_NAME="owner_name";
  private static final String REPO_NAME="repo_name";
  private  static final String DESCRIPTION="description";
  private static  final String HTML_URL="html_url";

    public RepoListDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME +
                "("+KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +OWNER_NAME + " TEXT,"
                +REPO_NAME + " TEXT,"
                +DESCRIPTION + " TEXT,"
                +HTML_URL+ " TEXT " +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //Method for insert operation
    public void addRepo(String ownerName, String repoName, String description, String html_url)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(OWNER_NAME,ownerName);
        values.put(REPO_NAME,repoName);
        values.put(DESCRIPTION,description);
        values.put(HTML_URL,html_url);
        db.insert(TABLE_NAME,null,values);

        db.close();
    }

    //Method for getting data
    public ArrayList<RepoModel> fetchRepo()
    {
      SQLiteDatabase db= this.getReadableDatabase();
      Cursor cursor= db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
      ArrayList<RepoModel> arrayList= new ArrayList<>();

      while(cursor.moveToNext())
      {
          RepoModel repoModel=new RepoModel();
          repoModel.repoId=cursor.getInt(0);
          repoModel.ownerName=cursor.getString(1);
          repoModel.repoName=cursor.getString(2);
          repoModel.description=cursor.getString(3);
          repoModel.html_url=cursor.getString(4);
          arrayList.add(repoModel);
      }
        db.close();
      return arrayList;

    }

    public void deleteRepo(int id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[] { String.valueOf(id) });
        db.close();
    }
}
