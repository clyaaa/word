package com.example.cly.word;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class NewsContentActivity extends AppCompatActivity {
    public static void actionStart(Context context, String newstitle,String newscontent, String newstime){
        Intent intent=new Intent( context,NewsContentActivity.class );
        intent.putExtra( "newstitle",newstitle );
        intent.putExtra( "newscontent",newscontent );
        intent.putExtra( "newstime",newstime );
        context.startActivity( intent );
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.news_content );
        String newstitle=getIntent().getStringExtra( "newstitle" );
        String newscontent=getIntent().getStringExtra( "newscontent" );
        String newstime=getIntent().getStringExtra( "newstime" );
        NewsContentFragment newsContentFragment=(NewsContentFragment)getSupportFragmentManager().findFragmentById( R.id.word_content_fragment );
        newsContentFragment.refresh(newstitle,newscontent,newstime);

    }
}
