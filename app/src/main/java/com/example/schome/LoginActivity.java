package com.example.schome;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {
    EditText editText_password;
    EditText editText_username;
    String username;
    String password;
    String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        QMUITopBar topBar = (QMUITopBar) findViewById(R.id.topbar);


        topBar.setTitle("账户登陆");
        topBar.addLeftImageButton(R.drawable.backpage, R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button button_back = (Button) findViewById(R.id.topbar_left_button);
        button_back.setBackgroundColor(Color.WHITE);
        topBar.addRightTextButton("帮助", R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText_username = (EditText) findViewById(R.id.edittext_name);
                editText_password = (EditText) findViewById(R.id.edittext_password);

                username = editText_username.getText().toString();
                password = editText_password.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        RequestBody requestBody = new FormBody.Builder()
                                .add("username", username)
                                .add("password", password)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://www.myweb-api.work:8080/project/LoginCheck")
                                .post(requestBody)
                                .build();
                        OkHttpClient client = new OkHttpClient();
                        Call call = client.newCall(request);
                        try {
                            Response response = call.execute();
                            if (response.isSuccessful()) {
                                responseData = response.body().string();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("Tag", responseData);
                        if (responseData.equals("200")) {
                            Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
                            startActivity(intent);
                            Log.d("Tag", "成功登录");
                        } else if (responseData.equals("201")) {
                            editText_password.setText("");
                            Log.d("Tag", "密码错误");
                        } else {

                            editText_username.setText("");
                            editText_password.setText("");
                            Log.d("Tag", "账户不存在");
                        }


                    }
                }).start();
            }
        });

    }
}
