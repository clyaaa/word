package com.example.cly.word;

import android.provider.BaseColumns;

public class DataBase implements BaseColumns{
    public DataBase(){

    }
    public static final String TABLE_NAME1="News_DB";
    public static final String COLUMN_NAME_WORD="name";
    public static final String COLUMN_NAME_MEANING="meaning";//单词含义
    public static final String COLUMN_NAME_SAMPLE="sample";//单词实例
}
