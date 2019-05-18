package com.example.cly.word;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;


public class first_three_fragment extends Fragment {
    GeneralDBHelper mDBHelper;
    WordAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate( R.layout.first_three_fragment, container, false );
        RecyclerView wordTitleRecyclerView = (RecyclerView)view.findViewById( R.id.word_title_recycler_view );
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        wordTitleRecyclerView.setLayoutManager(layoutManager);
        adapter=new WordAdapter(getNews(),3);
        wordTitleRecyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);
        registerForContextMenu( wordTitleRecyclerView );//注册上下文菜单
        mDBHelper=new GeneralDBHelper( getContext() );
        return view;
    }

    private List<News> getNews(){
        List<News> newsList=new ArrayList<>(  );
        GeneralDBHelper dbHelpermDBHelper=new GeneralDBHelper( getContext() );
        SQLiteDatabase db=dbHelpermDBHelper.getWritableDatabase();
        Cursor c;
        c=db.query( DataBase.TABLE_NAME1,null,"news_type='3'",null,null,null,null );
        if(c.moveToFirst()){
            do{
                int news_id=c.getInt( c.getColumnIndex( "news_id" ) );
                String news_title=c.getString( c.getColumnIndex( "news_title") );
                String news_content=c.getString( c.getColumnIndex( "news_content" ) );
                String news_time=c.getString( c.getColumnIndex( "news_time" ) );
                News news=new News();
                news.setNews_id( news_id );
                news.setNews_title( news_title );
                news.setNews_content( news_content );
                news.setNews_time( news_time );
                newsList.add( news );
            }while(c.moveToNext());
        }
        return newsList;
    }
    public void onDestroy(){
        super.onDestroy();
        mDBHelper.close();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(getActivity()).inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        News news;
        int id;
        switch(item.getItemId()){
            case R.id.action_delete:
                news=adapter.getItem(adapter.getPosition());
                id =news.getNews_id();
                DeleteDialog(id);
                break;

            case R.id.action_collect:
                news=adapter.getItem(adapter.getPosition());
                changecollect(news.getNews_id(),"yes");
                break;
        }
        return true;
    }
    private void changecollect(int id,String collect){
        SQLiteDatabase db=mDBHelper.getReadableDatabase();
        String sql="update "+DataBase.TABLE_NAME1+" set collect=? where news_id=?";
        String t=id+"";
        db.execSQL( sql,new String[]{collect,t} );
    }
    private void DeleteDialog(final int strid){
        new AlertDialog.Builder(getContext())
                .setTitle("删除文章")
                .setMessage("是否真的删除文章？")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {            //既可以使用Sql语句删除，也可以使用使用delete方法删除
                                DeleteUseSql(strid);
                                adapter.notifyDataSetChanged();
                                //Intent intent=new Intent(getContext(),MainActivity.class);
                                //startActivity(intent);
                                //setWordsListView(getAll());
                            }
                        })
                .setNegativeButton( "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                } )
                .create()
                .show();

    }
    private void DeleteUseSql(int strid){
        String sql="delete from "+DataBase.TABLE_NAME1+" where news_id= '"+strid+"'";
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        db.execSQL(sql);
    }


}