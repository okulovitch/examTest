package com.exam.com.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.exam.com.R;
import com.exam.com.adapters.UsersRecyclerAdapter;
import com.exam.com.model.User;
import com.exam.com.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ge62 on 06.01.2018.
 */

public class UsersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Button Enterbtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Button Enterbtn= findViewById(R.id.EnterHomebtn);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();

    }


    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }


    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }


    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();



    }

    public void onButtonClick(View v) {
        if(v.getId()==R.id.EnterHomebtn){
            Intent intent=new Intent (this,Home.class);
            startActivity(intent);
        }


    }



    public static class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void queryData(String sql){
            SQLiteDatabase database = getWritableDatabase();
            database.execSQL(sql);
        }

        public void insertData(String name, String price, byte[] image){
            SQLiteDatabase database = getWritableDatabase();
            String sql = "INSERT INTO FOOD VALUES (NULL, ?, ?, ?)";

            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();

            statement.bindString(1, name);
            statement.bindString(2, price);
            statement.bindBlob(3, image);

            statement.executeInsert();
        }

        public void updateData(String name, String price, byte[] image, int id) {
            SQLiteDatabase database = getWritableDatabase();

            String sql = "UPDATE FOOD SET name = ?, price = ?, image = ? WHERE id = ?";
            SQLiteStatement statement = database.compileStatement(sql);

            statement.bindString(1, name);
            statement.bindString(2, price);
            statement.bindBlob(3, image);
            statement.bindDouble(4, (double)id);

            statement.execute();
            database.close();
        }

        public  void deleteData(int id) {
            SQLiteDatabase database = getWritableDatabase();

            String sql = "DELETE FROM FOOD WHERE id = ?";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindDouble(1, (double)id);

            statement.execute();
            database.close();
        }

        public Cursor getData(String sql){
            SQLiteDatabase database = getReadableDatabase();
            return database.rawQuery(sql, null);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
