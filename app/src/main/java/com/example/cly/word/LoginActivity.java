package com.example.cly.word;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button bt_log;
    private Button bt_bos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //利用布局资源文件设置用户界面
        setContentView(R.layout.login);

        //通过资源标识获得控件实例
        et_username = (EditText) findViewById(R.id.account);
        et_password = (EditText) findViewById(R.id.pwd);
        bt_log = (Button) findViewById(R.id.login);
        bt_bos = (Button) findViewById(R.id.quit);

        //给登录按钮注册监听器，实现监听器接口，编写事件
        bt_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入的数据
                String strUsername = et_username.getText().toString();
                String strPassword = et_password.getText().toString();

                //判断用户名和密码是否正确（为可以进行测试，将用户名和密码都定义为admin）
                if(strUsername.equals("admin") && strPassword.equals("admin")){
                    //Toast.makeText("用户名和密码正确！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    //Toast.makeText(MainActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //给取消按钮注册监听器，实现监听器接口，编写事件
        bt_bos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

