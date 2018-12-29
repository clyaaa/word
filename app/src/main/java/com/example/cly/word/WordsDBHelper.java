package com.example.cly.word;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordsDBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="wordsdb";
    private final static int DATABASE_VERSION=1;
    private final static String SQL_CREATE_DATABASE="CREATE TABLE "+
            Words.Word.TABLE_NAME+"("+
            "id INTEGER," +
            "name TEXT,"+
            "meaning TEXT,"+
            "sample TEXT,"+
            "collect TEXT,"+
            "updatetime date"+")";
    private final static String SQL_DELETE_DATABASE="DROP TABLE IF EXISTS "+Words.Word.TABLE_NAME;

    public WordsDBHelper(Context context){
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
