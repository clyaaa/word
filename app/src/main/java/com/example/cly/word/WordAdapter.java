package com.example.cly.word;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public interface  HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }

}