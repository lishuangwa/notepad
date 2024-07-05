package com.lishuang.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lishuang.myapp.dao.Dao;
import com.lishuang.myapp.model.User;
import com.lishuang.myapp.service.UserServiceImpl;

public class LoginActivity extends AppCompatActivity {

    private Button login, register;
    private CheckBox remember;
    private EditText username, password;
    private UserServiceImpl userService;
    private Dao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        initView();
        displayLoginInfo();
        userDao = new Dao(this);
        // 登录按钮
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                String pwd = password.getText().toString();
                boolean checked = remember.isChecked();
                // 判断输入框中 是否有值 和用户名是否正确
                boolean loginRes = false;
                if (!uname.isEmpty() && !pwd.isEmpty()) {
                    userService = new UserServiceImpl();
                    loginRes = userService.login(userDao, new User(uname, pwd));
                }

                if (!loginRes) {
                    Toast toast = Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    username.setHintTextColor(Color.RED);
                    password.setHintTextColor(Color.RED);
                    username.setText("");
                    password.setText("");
                    toast.show();
                    return;
                }
                // 记住密码存值
                SharedPreferences.Editor editor = getSharedPreferences("myLoginInfo", 0).edit();
                editor.putString("loginUsername", uname);
                editor.putString("loginPassword", pwd);
                editor.putBoolean("loginRemember", checked);
                editor.commit();
                // 页面跳转
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 注册按钮
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 页面跳转
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayLoginInfo() {
        String loginUsername = getSharedPreferences("myLoginInfo", 0).getString("loginUsername", "");
        String loginPassword = getSharedPreferences("myLoginInfo", 0).getString("loginPassword", "");
        boolean loginRemember = getSharedPreferences("myLoginInfo", 0).getBoolean("loginRemember", false);
        if (loginRemember) {
            username.setText(loginUsername);
            password.setText(loginPassword);
            remember.setChecked(true);
        }
    }

    private void initView() {
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        remember = findViewById(R.id.remember);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }
}
