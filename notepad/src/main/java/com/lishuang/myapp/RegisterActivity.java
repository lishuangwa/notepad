package com.lishuang.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lishuang.myapp.dao.Dao;
import com.lishuang.myapp.model.User;
import com.lishuang.myapp.service.UserServiceImpl;

public class RegisterActivity extends AppCompatActivity {
    Dao userDao;
    UserServiceImpl userService;
    private Button registerBtn;
    private EditText registerUsername, registerPassword, checkPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        userDao = new Dao(this);
        initView();
        // 注册按钮
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 判断两次密码是否一样
                String pwd = registerPassword.getText().toString();
                String checkPwd = checkPassword.getText().toString();
                String uname = registerUsername.getText().toString();
                userService = new UserServiceImpl();
                // 新增
                Long l = userService.insertUser(userDao, new User(uname, pwd));

                // todo 是否存在 参数校验
//                userService.exist(userDao, "1");

//                if (!pwd.equals(checkPwd)) {
//                    Toast toast = Toast.makeText(RegisterActivity.this, "两次密码不一致!", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    registerUsername.setHintTextColor(Color.RED);
//                    registerPassword.setHintTextColor(Color.RED);
//                    registerUsername.setText("");
//                    registerPassword.setText("");
//                    checkPassword.setText("");
//                    toast.show();
//                    return;
//                }
//                // 判断账号是否重复
//                boolean systemUser = isSystemUser(uname);
//                // 新增
//                SQLiteDatabase db = userDao.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put(UserDao.USERNAME, uname);
//                values.put(UserDao.PASSWORD, pwd);
//                long insert = db.insert(UserDao.TABLE, null, values);
                // 判断成功就跳转登录页
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        registerBtn = findViewById(R.id.registerPageBtn);
        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        checkPassword = findViewById(R.id.checkPassword);
    }
}
