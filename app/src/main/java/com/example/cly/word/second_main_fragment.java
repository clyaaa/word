package com.example.cly.word;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class second_main_fragment extends Fragment {

    GeneralDBHelper mDBHelper;
    WordAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.second_main, container, false );
        RecyclerView wordTitleRecyclerView = (RecyclerView)view.findViewById( R.id.word_title_recycler_view );
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        wordTitleRecyclerView.setLayoutManager(layoutManager);
        adapter=new WordAdapter(getNews(),4);
        wordTitleRecyclerView.setAdapter(adapter);
        mDBHelper=new GeneralDBHelper( getActivity());
        return view;

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
                adapter.notifyDataSetChanged();

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
        GeneralDBHelper dbHelpermDBHelper = new GeneralDBHelper(getActivity());
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
}
