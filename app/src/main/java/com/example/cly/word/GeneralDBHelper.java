package com.example.cly.word;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GeneralDBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="generous_db";
    private final static int DATABASE_VERSION=1;
    private final static String SQL_CREATE_DATABASE="CREATE TABLE "+
            DataBase.TABLE_NAME1+"("+
            "news_id INTEGER," +
            "news_title TEXT,"+
            "news_content TEXT,"+
            "news_time date,"+
            "news_address TEXT,"+
            "news_img TEXT,"+
            "news_type INTEGER,"+
            "collect TEXT"+")";
    private final static String SQL_DELETE_DATABASE="DROP TABLE IF EXISTS "+DataBase.TABLE_NAME1;

    public GeneralDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase sqlLiteDatabase){
        sqlLiteDatabase.execSQL( SQL_CREATE_DATABASE );
    }
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int oldVersion,int newVersion){
        sqLiteDatabase.execSQL( SQL_DELETE_DATABASE );
        onCreate( sqLiteDatabase );
    }

    }
