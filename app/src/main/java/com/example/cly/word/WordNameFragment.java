package com.example.cly.word;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class WordNameFragment extends Fragment {
    WordsDBHelper mDBHelper;
    WordAdapter adapter;
    boolean shunxu=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_word_title, container, false );
        RecyclerView wordTitleRecyclerView = (RecyclerView)view.findViewById( R.id.word_title_recycler_view );
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        wordTitleRecyclerView.setLayoutManager(layoutManager);
        adapter=new WordAdapter(getWord(shunxu));
        wordTitleRecyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);
        registerForContextMenu( wordTitleRecyclerView );//注册上下文菜单
        mDBHelper=new WordsDBHelper( getContext() );
        return view;
    }

    class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
        private List<Word> mWordList;
        private int mPosition = -1;

        public int getPosition() {
            return mPosition;
        }
        public WordAdapter(List<Word> mWordList){
            this.mWordList=mWordList;
        }
        public void removeItem(int position) {
            mWordList.remove(position);
            notifyDataSetChanged();
        }
        public Word getItem(int position){
            return mWordList.get( position );
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView wordNameText;
            TextView wordMeaningText;
            TextView wordUpdateText;
            TextView wordcollect;
            public ViewHolder(View view){
                super(view);
                wordNameText=(TextView)view.findViewById( R.id.word_name );
                wordMeaningText=(TextView)view.findViewById( R.id.word_meaning );
                wordUpdateText=(TextView)view.findViewById( R.id.word_update );
                wordcollect=(TextView)view.findViewById(R.id.wordcollect);
            }
        }
        public void update(List<Word> mWordList){
            this.mWordList=mWordList;
            notifyDataSetChanged();
        }
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view=LayoutInflater.from( parent.getContext() ).inflate( R.layout.word_item,parent,false );
            final ViewHolder holder=new ViewHolder( view );
            view.setOnClickListener( new View.OnClickListener(){
                public void onClick(View v){
                    Word word=mWordList.get( holder.getAdapterPosition() );
                    WordContentActivity.actionStart( getActivity(),word.getName(),word.getMeaning(),word.getSample(),word.getUpdate() );
                }
            } );
            return holder;

        }

        @Override
        /*public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Word word=mWordList.get(position);
            holder.wordNameText.setText(word.getName());
            holder.wordMeaningText.setText( word.getMeaning() );

        }*/
        public void onBindViewHolder(final WordAdapter.ViewHolder holder, final int position) {
            Word word=mWordList.get(position);
            holder.wordNameText.setText(word.getName());
            holder.wordMeaningText.setText( word.getMeaning() );
            holder.wordUpdateText.setText(word.getUpdate());
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
            return mWordList.size();
        }

    }
    private List<Word> getWord(boolean shunxu){//true是按时间排序，false是按首字母
        /*List<Word> wordList=new ArrayList <>(  );
        WordsDBHelper dbHelper;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(int i=1;i<=50;i++){
            Word word=new Word();
            word.setName( "This is word title"+i );
            word.setMeaning( "123" );
            word.setSample( getRandomLengthContent("This is word content"+i+".") );
            wordList.add(word);
        }
        return wordList;*/
        List<Word> wordList=new ArrayList <>(  );
        WordsDBHelper dbHelpermDBHelper=new WordsDBHelper( getContext() );
        SQLiteDatabase db=dbHelpermDBHelper.getWritableDatabase();
        Cursor c;
        if(shunxu){
             c=db.query( Words.Word.TABLE_NAME,null,"collect = 'no'",null,null,null,"updatetime" );
        }else{
             c=db.query( Words.Word.TABLE_NAME,null,"collect = 'no'",null,null,null,"name" );
        }
        //Cursor cursor = db.query("person", new String[]{"personid,name,age"}, "name like?", new String[]{"%传智%"}, null, null, "personid desc", "1,2");
        //query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
        if(c.moveToFirst()){
            do{
                int id=c.getInt( c.getColumnIndex( "id" ) );
                String name=c.getString( c.getColumnIndex( "name") );
                String meaning=c.getString( c.getColumnIndex( "meaning" ) );
                String sample=c.getString( c.getColumnIndex( "sample" ) );
                String updatetime=c.getString( c.getColumnIndex( "updatetime" ) );
                String collect=c.getString(c.getColumnIndex("collect"));
                Word word=new Word();
                word.setId( id );
                word.setName( name );
                word.setMeaning( meaning );
                word.setSample( sample );
                word.setUpdate( updatetime );
                word.setCollect(collect);
                wordList.add( word );
            }while(c.moveToNext());
        }
        return wordList;
    }
    private String getRandomLengthContent(String content){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }
    public void onDestroy(){
        super.onDestroy();
        mDBHelper.close();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch(id){
            case R.id.action_search:
                SearchDialog();
                return true;
            case R.id.action_insert:
                InsertDialog();
                return true;
            case R.id.shunxu:
                shunxu=!shunxu;
                adapter.update(getWord(shunxu));//不能直接动构造方法
                return true;
            case R.id.collect:
                Intent intent=new Intent(getContext(),collectActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected( item );
    }
   /* public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){//创建上下文菜单
        getMenuInflater().inflate( R.menu.contextmenu_wordslistview,menu );
        //menu.add( 1,1,1,"删除" );
        //menu.add(1,2,1,"修改");
    }*/
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(getActivity()).inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView textId=null;
        TextView textWord=null;
        TextView textMeaning=null;
        TextView textSample=null;
        //AdapterView.AdapterContextMenuInfo info=null;
        View itemView=null;
        Word word;
        int id;
        switch(item.getItemId()){
            //case 1:
            case R.id.action_delete:
                //info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();    课件上的东西没什么卵用
                //adapter.removeItem(adapter.getPosition());
                word=adapter.getItem(adapter.getPosition());
                id =word.getId();
                DeleteDialog(id);
                break;
                /*itemView=info.targetView;
                //textId=(TextView)itemView.findViewById(R.id.textId);
                textWord =(TextView)itemView.findViewById(R.id.word_name);
                if(textWord!=null){
                    String strWord=textWord.getText().toString();
                    DeleteDialog(strWord);
                }
                break;*/
            //case 2:
            case R.id.action_update:
                //Toast.makeText(getContext(),"更新",Toast.LENGTH_LONG).show();
                /*info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;
                //textId=(TextView)itemView.findViewById( R.id.textId );
                textWord =(TextView)itemView.findViewById(R.id.word_name);
                textMeaning =(TextView)itemView.findViewById(R.id.word_meaning);
                //textSample =(TextView)itemView.findViewById(R.id.textViewSample);
                if(textWord!=null && textMeaning!=null ){
                    //String strId=textId.getText().toString();
                    String strWord=textWord.getText().toString();
                    String strMeaning=textMeaning.getText().toString();
                    //String strSample=textSample.getText().toString();
                    UpdateDialog( strWord, strMeaning);
                }
                break;*/
                word=adapter.getItem(adapter.getPosition());
                id=word.getId();
                String name =word.getName();
                String meaning=word.getMeaning();
                String sample=word.getSample();
                UpdateDialog(id,name,meaning,sample);
                break;
            case R.id.action_collect:
                word=adapter.getItem(adapter.getPosition());
                id=word.getId();
                changecollect(id,"yes");
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
    /*private void InsertUserSql(String strWord, String strMeaning, String strSample){
        String sql="insert into  words(word,meaning,sample) values(?,?,?)";
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL(sql,new String[]{strWord,strMeaning,strSample});
    }*/
    private void Insert(String strWord, String strMeaning, String strSample) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        //String sql="select MAX(?) id from wordDB";
        String sqls="select id from wordDB ";
        //Cursor c=db.rawQuery( sql ,new String[]{"id"});
        Cursor c=db.rawQuery(sqls,null);
        ContentValues values = new ContentValues();
        int maxid=-1;
        if(c.moveToFirst()){
            do{
                int id=c.getInt( c.getColumnIndex( "id" ) );
                if(id>maxid){
                    maxid=id;
                }
            }while(c.moveToNext());
        }
        maxid+=1;
        values.put("id",maxid);
        values.put( "name", strWord );
        values.put( "meaning", strMeaning );
        values.put( "sample", strSample );
        values.put("collect","no");
        values.put( "updatetime",simpleDateFormat.format(date) );
        long newRowId;
        newRowId = db.insert(Words.Word.TABLE_NAME,null,values);
    }
    private void InsertDialog(){
        final TableLayout tableLayout=(TableLayout)getLayoutInflater().inflate(R.layout.insert,null);
        new AlertDialog.Builder(getContext())
                .setTitle( "新增单词" )
                .setView(tableLayout)
                .setPositiveButton( "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strWord=((EditText)tableLayout.findViewById( R.id.editWord )).getText().toString();
                        String strMeaning=((EditText)tableLayout.findViewById(R.id.editMeaning)).getText().toString();
                        String strSample=((EditText)tableLayout.findViewById(R.id.editSample)).getText().toString();
                        Insert(strWord,strMeaning,strSample);
                        Intent intent=new Intent(getContext(),MainActivity.class);
                        startActivity(intent);
                    }
                } )
                .setNegativeButton( "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                } )
                .create()
                .show();
    }
    private void DeleteUseSql(int strid){
        String sql="delete from wordDB where id= '"+strid+"'";
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        db.execSQL(sql);
    }
    /*private void Delete(String strId){
        SQLiteDatabase db=mDBHelper.getReadableDatabase();
        String selection =Words.Word._ID+"=?";
        String[] selectionArgs={strId};
        db.delete(Words.Word.TABLE_NAME,selection,selectionArgs);
    }*/
    private void DeleteDialog(final int strid){
        new AlertDialog.Builder(getContext())
                .setTitle("删除单词")
                .setMessage("是否真的删除单词？")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {            //既可以使用Sql语句删除，也可以使用使用delete方法删除
                                DeleteUseSql(strid);
                                Intent intent=new Intent(getContext(),MainActivity.class);
                                startActivity(intent);
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
    private void changecollect(int id,String collect){
        SQLiteDatabase db=mDBHelper.getReadableDatabase();
        String sql="update wordDB set collect=? where id=?";
        String t=id+"";
        db.execSQL( sql,new String[]{collect,t} );
    }
    private void UpdateUseSql(int id,String strWord,String strMeaning,String strNewSample){
        SQLiteDatabase db=mDBHelper.getReadableDatabase();
        String sql="update wordDB set name = ?,meaning=?,sample=? where id=?";
        String t=id+"";
        db.execSQL( sql,new String[]{strWord,strMeaning,strNewSample,t} );
    }
    /*private void Update(String strId,String strWord,String strMeaning,String strSample){
        SQLiteDatabase db=mDBHelper.getReadableDatabase();
        ContentValues values=new ContentValues(  );
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);
        String selection = Words.Word._ID + " = ?";
        String[] selectionArgs = {strId};
        int count = db.update(Words.Word.TABLE_NAME,values,selection,selectionArgs);
    }*/
    private void UpdateDialog(final int id, final String strName, final String strMeaning,final String strSample) {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        ((EditText)tableLayout.findViewById(R.id.editWord)).setText(strName);
        ((EditText)tableLayout.findViewById(R.id.editMeaning)).setText(strMeaning);
        ((EditText)tableLayout.findViewById(R.id.editSample)).setText(strSample);
        new AlertDialog.Builder(getContext())
                .setTitle("修改单词")//标题
                .setView(tableLayout)//设置视图以及原本的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strNewWord = ((EditText) tableLayout.findViewById(R.id.editWord)).getText().toString();//获得新修改之后的
                        String strNewMeaning = ((EditText) tableLayout.findViewById(R.id.editMeaning)).getText().toString();
                        String strNewSample = ((EditText) tableLayout.findViewById(R.id.editSample)).getText().toString();
                        UpdateUseSql(id, strNewWord, strNewMeaning,strNewSample);
                        Intent intent=new Intent(getContext(),MainActivity.class);
                        startActivity(intent);
                        //setWordsListView(getAll());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()//创建对话框
                .show();//显示对话框
    }
    private List<Word> SearchUseSql(String strWordSearch) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql="select * from wordDB where name = ? ";
        Cursor c=db.rawQuery(sql,new String[]{strWordSearch});
        List<Word> list=new ArrayList<>(  );
        if(c.moveToFirst()){
            do{
                int id=c.getInt( c.getColumnIndex( "id") );
                String name=c.getString( c.getColumnIndex( "name" ) );
                String meaning=c.getString( c.getColumnIndex( "meaning" ) );
                String sample=c.getString( c.getColumnIndex( "sample" ) );
                Word word=new Word();
                word.setId( id );
                word.setName( name );
                word.setMeaning( meaning );
                word.setSample( sample );
                list.add(word);
            }while(c.moveToNext());
        }
        return list;
    }
    /*private List<Word> Search(String strWordSearch) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {"id", Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE};
        String sortOrder = Words.Word.COLUMN_NAME_WORD + " DESC";
        String selection = Words.Word.COLUMN_NAME_WORD + " LIKE ?";
        String[] selectionArgs = {"%" + strWordSearch + "%"};
        Cursor c = db.query( "wordDB", projection, selection, selectionArgs, null, null, sortOrder );
        List<Word> list=null;
        if(c.moveToFirst()){
            do{
                int id=c.getInt( c.getColumnIndex( "id" ) );
                String name=c.getString( c.getColumnIndex( "name" ) );
                String meaning=c.getString( c.getColumnIndex( "meaning" ) );
                String sample=c.getString( c.getColumnIndex( "sample" ) );
                Word word=new Word();
                word.setId( id );
                word.setName( name );
                word.setMeaning( meaning );
                word.setSample( sample );
                list.add(word);
            }while(c.moveToNext());
        }
        return list;
    }*/
    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(getContext())
                .setTitle("查找单词")//标题
                .setView(tableLayout)//设置视图                    //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchWord=((EditText)tableLayout.findViewById(R.id.editsearch)).getText().toString();
                        List<Word> items=null;                    //既可以使用Sql语句查询，也可以使用方法查询
                        items=SearchUseSql(txtSearchWord);
                        //items=Search(txtSearchWord);
                        if(items!=null) {
                            Word item=items.get( 0);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("result", item );
                            Intent intent=new Intent(getContext(),SearchActivity.class);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        }else
                            Toast.makeText(getContext(),"没有找到",Toast.LENGTH_LONG).show();
                    }
                })                    //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }            })
                .create()//创建对话框
                .show();//显示对话框
    }
}
