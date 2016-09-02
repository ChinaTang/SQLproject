package com.di.tang.sqlproject.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.di.tang.sqlproject.R;
import com.di.tang.sqlproject.sql.Database;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button insert1, insert2;
    private Database mDatabase;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insert1 = (Button)findViewById(R.id.insert1);
        insert2 = (Button)findViewById(R.id.insert2);
        insert1.setOnClickListener(this);
        insert2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.insert1 :
                ContentValues contentValues = new ContentValues();
                int i = new Random().nextInt();
                contentValues.put("name", "nameof" + i);
                contentValues.put("detail", "detail" + i);
                sqLiteDatabase.insert(Database.TABLE1, null, contentValues);
                break;
            case R.id.insert2 :
                ContentValues contentValues1 = new ContentValues();
                int j = new Random().nextInt();
                contentValues1.put("name", "nameof" + j);
                contentValues1.put("detail", "detail" + j);
                sqLiteDatabase.insert(Database.TABLE2, null, contentValues1);
                break;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        sqLiteDatabase.close();
        mDatabase.close();
    }

    @Override
    public void onResume(){
        super.onResume();
        mDatabase = new Database(this);
        sqLiteDatabase = mDatabase.getWritableDatabase();
    }
}
