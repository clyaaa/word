package com.example.cly.word;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Fragment fragment1=new first_main_fragment();
    Fragment fragment2=new second_main_fragment();
    Fragment fragment3=new three_main_fragment();
    Fragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        TextView first=(TextView) findViewById( R.id.first );
        TextView second=(TextView) findViewById( R.id.second );
        TextView three=(TextView) findViewById( R.id.three );
        first.setOnClickListener( this );
        second.setOnClickListener( this );
        three.setOnClickListener( this );
        mContent=fragment1;
        //replaceFragment( fragment1 );
        switchContent(fragment1);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.first:
                //replaceFragment( fragment1 );
                switchContent(fragment1);
                break;
            case R.id.second:
                //replaceFragment( fragment2 );
                switchContent(fragment2);
                break;
            case R.id.three:
                //replaceFragment( fragment3 );
                switchContent(fragment3);
                break;
            default:
                break;
        }
    }
    private void switchContent(Fragment to) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(to!=mContent){
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(mContent).add(R.id.main_layout, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(mContent).show(to).commit();
            }
            mContent = to;
        }else{
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.add(R.id.main_layout, to).show(to).commit();
            }

        }

    }
    public void replaceFragment(Fragment fragment){//会重新过一遍activity的周期，推荐用上面那个
        FragmentManager fragmentManager=getSupportFragmentManager();
        //Right_Fragment rightFragment=(Right_Fragment)getSupportFragmentManager().findFragmentById( R.id.fragment_right_ ); //从布局文件当中获取碎片
        FragmentTransaction transaction=fragmentManager.beginTransaction();//开启事件
        //transaction.show( fragment );
        transaction.replace( R.id.main_layout,fragment );
        transaction.addToBackStack( null );//设置可返回
        transaction.commit();//提交事件
    }
}
