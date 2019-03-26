package com.example.schome;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qmuiteam.qmui.widget.QMUITopBar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    String username;
    String password;
    EditText editText_password;
    EditText editText_username;
    String responseData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        QMUITopBar topBar = (QMUITopBar)findViewById(R.id.topbar);
        topBar.setTitle("账户注册");
        topBar.addLeftImageButton(R.drawable.backpage,R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button button = (Button)findViewById(R.id.topbar_left_button);
        button.setBackgroundColor(Color.WHITE);
        topBar.addRightTextButton("帮助",R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button button_register = (Button)findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_username = (EditText)findViewById(R.id.edittext_name);
                editText_password = (EditText)findViewById(R.id.edittext_password);
                username = editText_username.getText().toString();
                password = editText_password.getText().toString();
                new Thread(new Runnable(){
                    @Override
                    public void run() {

                        RequestBody requestBody = new FormBody.Builder()
                                .add("username", username)
                                .add("password", password)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://www.myweb-api.work:8080/project/RegisterCheck")
                                .post(requestBody)
                                .build();
                        OkHttpClient client = new OkHttpClient();
                        Call call = client.newCall(request);
                        try{
                            Response response = call.execute();
                            if(response.isSuccessful()){
                                responseData = response.body().string();
                            }
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        Log.d("Tag",responseData);
                        if(responseData.equals("200")){
                            Intent intent = new Intent(RegisterActivity.this,FirstActivity.class);
                            startActivity(intent);
                            finish();
                            Log.d("Tag","成功注册");
                        }
                        else if(responseData.equals("201")){
                            editText_password.setText("");
                            Log.d("Tag","用户存在");
                        }
                        else
                        {
                            editText_username.setText("");
                            editText_password.setText("");
                            Log.d("Tag","系统错误");
                        }


                    }
                }).start();
            }
        });




    }
}
