package com.example.cly.word;
import com.example.cly.word.first_second_fragment;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/*public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}*/
public class HttpUtil {
    public static void sendHttpRequest(final int type,final String address,final WordAdapter.HttpCallbackListener listener){
        new Thread( new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod( "POST" );
                    connection.setConnectTimeout( 8000 );
                    connection.setReadTimeout( 8000 );
                    //connection.setDoInput( true );
                    //connection.setDoOutput( true );

                    /*DataOutputStream out = new DataOutputStream(connection
                            .getOutputStream());
                    // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
                    String content = "aaa" + URLEncoder.encode("Is is a test", "UTF-8");
                    // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
                    out.writeBytes(content);
                    //流用完记得关
                    out.flush();*/

                    //InputStream in=connection.getInputStream();

                    //String[] arr = {"aaa","bbb","ccc","ddd"};
                    ObjectOutputStream oos = new ObjectOutputStream(connection
                            .getOutputStream());
                    oos.writeObject(type);//写入输出对象
                    oos.flush();
                    oos.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response=new StringBuilder(  );
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append( line );
                    }
                    if(listener!=null){
                        listener.onFinish( response.toString() );//网络获取成功时
                    }
                }catch(Exception e){
                    listener.onError( e );//网络获取失败时
                }finally{
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        } ).start();
    }
    public static void sendHttpRequest(final int type,final String address,final second_main_fragment.HttpCallbackListener listener){
        new Thread( new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod( "POST" );
                    connection.setConnectTimeout( 8000 );
                    connection.setReadTimeout( 8000 );
                    //connection.setDoInput( true );
                    //connection.setDoOutput( true );

                    /*DataOutputStream out = new DataOutputStream(connection
                            .getOutputStream());
                    // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
                    String content = "aaa" + URLEncoder.encode("Is is a test", "UTF-8");
                    // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
                    out.writeBytes(content);
                    //流用完记得关
                    out.flush();*/

                    //InputStream in=connection.getInputStream();

                    //String[] arr = {"aaa","bbb","ccc","ddd"};
                    ObjectOutputStream oos = new ObjectOutputStream(connection
                            .getOutputStream());
                    oos.writeObject(type);//写入输出对象
                    oos.flush();
                    oos.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response=new StringBuilder(  );
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append( line );
                    }
                    if(listener!=null){
                        listener.onFinish( response.toString() );//网络获取成功时
                    }
                }catch(Exception e){
                    listener.onError( e );//网络获取失败时
                }finally{
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        } ).start();
    }
    public static void sendHttpRequest(final int type,final String address,final CollectActivity.HttpCallbackListener listener){
        new Thread( new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod( "POST" );
                    connection.setConnectTimeout( 8000 );
                    connection.setReadTimeout( 8000 );
                    //connection.setDoInput( true );
                    //connection.setDoOutput( true );

                    /*DataOutputStream out = new DataOutputStream(connection
                            .getOutputStream());
                    // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
                    String content = "aaa" + URLEncoder.encode("Is is a test", "UTF-8");
                    // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
                    out.writeBytes(content);
                    //流用完记得关
                    out.flush();*/

                    //InputStream in=connection.getInputStream();

                    //String[] arr = {"aaa","bbb","ccc","ddd"};
                    ObjectOutputStream oos = new ObjectOutputStream(connection
                            .getOutputStream());
                    oos.writeObject(type);//写入输出对象
                    oos.flush();
                    oos.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response=new StringBuilder(  );
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append( line );
                    }
                    if(listener!=null){
                        listener.onFinish( response.toString() );//网络获取成功时
                    }
                }catch(Exception e){
                    listener.onError( e );//网络获取失败时
                }finally{
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        } ).start();
    }
    public static void changecollect(final int id,final boolean flag,final String address,final first_first_fragment.HttpCallbackListener listener){
        final Map<String,Object> map = new LinkedHashMap<String,Object>();
        map.put("id",id);
        map.put("flag",flag);
        new Thread( new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod( "POST" );
                    connection.setConnectTimeout( 8000 );
                    connection.setReadTimeout( 8000 );
                    ObjectOutputStream oos = new ObjectOutputStream(connection
                            .getOutputStream());

                    oos.writeObject(map);//写入输出对象
                    oos.flush();
                    oos.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response=new StringBuilder(  );
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append( line );
                    }
                    if(listener!=null){
                        listener.onFinish( response.toString() );//网络获取成功时
                    }
                }catch(Exception e){
                    listener.onError( e );//网络获取失败时
                }finally{
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        } ).start();
    }
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){//另外一种网络请求方式
        OkHttpClient client=new OkHttpClient();
        Request request =new Request.Builder()
                .url(address)
                .build();
        client.newCall( request ).enqueue( callback );//enqueue内部已经开启了子线程
    }
}
