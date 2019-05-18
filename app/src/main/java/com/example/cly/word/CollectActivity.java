package com.example.cly.word;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectActivity extends AppCompatActivity {

    GeneralDBHelper mDBHelper;
    WordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.collect );
        RecyclerView wordTitleRecyclerView = (RecyclerView)findViewById( R.id.word_title_recycler_view );
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        wordTitleRecyclerView.setLayoutManager(layoutManager);
        adapter=new WordAdapter(getNews(),5);
        wordTitleRecyclerView.setAdapter(adapter);
        //setHasOptionsMenu(true);
        registerForContextMenu( wordTitleRecyclerView );//注册上下文菜单
        mDBHelper=new GeneralDBHelper( this);
    }
    public static void actionStart(Context context){
        Intent intent=new Intent( context,CollectActivity.class );
        context.startActivity( intent );
    }
    class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
        private List<News> mNewsList;
        private int mPosition = 0;
        int type=1;

        public WordAdapter(List<News> mNewsList,int type){//type第几个界面
            this.mNewsList=mNewsList;
            this.type=type;
            new MyFoodTask().execute();
        }
        public int getPosition() {
            return mPosition;
        }

        public void removeItem(int position) {
            mNewsList.remove(position);
            notifyDataSetChanged();
        }
        public News getItem(int position){
            return mNewsList.get( position );
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView newstitleText;
            TextView newstimeText;
            public ViewHolder(View view){
                super(view);
                newstitleText=(TextView)view.findViewById( R.id.news_title );
                newstimeText=(TextView)view.findViewById( R.id.news_time );
            }
        }
        public void update(List<News> mNewsList){
            this.mNewsList=mNewsList;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
            View view=LayoutInflater.from( parent.getContext() ).inflate( R.layout.news_item,parent,false );
            final ViewHolder holder=new ViewHolder( view );
            view.setOnClickListener( new View.OnClickListener(){
                public void onClick(View v){
                    News news=mNewsList.get( holder.getAdapterPosition() );
                    NewsContentActivity.actionStart( parent.getContext(),news.getNews_title(),news.getNews_content(),news.getNews_time());
                }
            } );
            return holder;

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            News news=mNewsList.get(position);
            holder.newstitleText.setText(news.getNews_title());
            holder.newstimeText.setText( news.getNews_time() );
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mPosition =holder.getAdapterPosition();
                    return false;
                }
            });

        }


        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        private class MyFoodTask extends AsyncTask<String, Void, Map<String,Object>> {
            @Override
            protected void onPreExecute() {

            }
            @Override
            protected Map <String, Object> doInBackground(String... params) {


                String path = "http://192.168.43.93:8080/Web/Test";
                HttpUtil.sendHttpRequest(type,path,new HttpCallbackListener(){
                    @Override
                    public void onFinish(String response) {//成功时的处理方法
                        try {
                            Map <String, Object> map=parseJson( response );
                            mNewsList.addAll( (List<News>) map.get("newslist") );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Exception e){//失败时的处理方法

                    }
                });
                return null;
            }


            @Override
            protected void onPostExecute(Map <String, Object> result) {
                //adapter.mNewsList=(List<News>)result.get("foodList");
                //adapter.notifyDataSetChanged();

            }


            private Map <String, Object> parseJson(String json) throws Exception {

                Map <String, Object> result = new HashMap<String, Object>();
                ArrayList<News> lists = new ArrayList <News>();
                JSONObject bigObj = new JSONObject( json );
                JSONArray array = bigObj.getJSONArray( "newslist" );
                News n = null;
                for (int i = 0; i < array.length(); i++) {
                    n = new News();
                    JSONObject smallObj = array.getJSONObject( i );

                    n.setNews_id( smallObj.getInt( "news_id" ) );
                    n.setNews_title( smallObj.getString( "news_title" ) );
                    n.setNews_content( smallObj.getString( "news_content" ) );
                    n.setNews_type( smallObj.getInt( "news_type" ) );
                    lists.add( n );
                }
                result.put( "newslist", lists );
                return result;


            }
        }

    }


    public interface  HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }
    private List<News> getNews() {
        List<News> wordList = new ArrayList<>();
        GeneralDBHelper dbHelpermDBHelper = new GeneralDBHelper(this);
        SQLiteDatabase db = dbHelpermDBHelper.getWritableDatabase();
        Cursor c;
        c = db.query(DataBase.TABLE_NAME1, null, "collect = 'yes'", null, null, null,null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex("news_id"));
                String news_title = c.getString(c.getColumnIndex("news_title"));
                String news_time = c.getString(c.getColumnIndex("news_time"));
                News news = new News();
                news.setNews_id(id);
                news.setNews_title(news_title);
                news.setNews_time(news_time);
                wordList.add(news);
            } while (c.moveToNext());
        }
        return wordList;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(this).inflate(R.menu.menu_context_test, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        News news;
        int id;
        switch(item.getItemId()){
            case R.id.collect:
                news=adapter.getItem(adapter.getPosition());
                id=news.getNews_id();
                changecollect(id,false);
                adapter.update(getNews());
                break;
        }
        return true;
    }
    private void changecollect(int id,boolean collect){
        String path = "http://192.168.43.93:8080/Web/Test2";
        HttpUtil.changecollect(id,collect,path,new first_first_fragment.HttpCallbackListener(){
            @Override
            public void onFinish(String response) {//成功时的处理方法

            }
            @Override
            public void onError(Exception e){//失败时的处理方法

            }
        });
    }

}
