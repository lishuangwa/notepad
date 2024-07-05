package com.lishuang.myapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lishuang.myapp.dao.Dao;
import com.lishuang.myapp.model.Note;
import com.lishuang.myapp.service.NoteServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private Button addBtn, upload;
    private EditText etTitle, etDate, etContent;
    private ImageView ivImage;
    private Uri selectedImageUri;
    private Dao dao;
    private NoteServiceImpl noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        initView();
        dao = new Dao(this);
        // 时间
        SimpleDateFormat sfd = new SimpleDateFormat();
        sfd.applyPattern("yyyy-MM-dd HH:mm:ss");
        String resDate = sfd.format(new Date());
        etDate.setText(resDate);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 保存信息
                String title = etTitle.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                String img = imgString();
                noteService = new NoteServiceImpl();
                Long count = noteService.insertNote(dao, new Note(title, date, content, img));
                if (count <= 0) {
                    Toast.makeText(AddActivity.this, "添加上失败!", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(AddActivity.this, "成功添加!", Toast.LENGTH_LONG).show();
                // 页面跳转
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 打开相册
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ivImage.setImageURI(selectedImageUri);
        }
    }

    private void initView() {
        addBtn = findViewById(R.id.btn_add);
        upload = findViewById(R.id.btn_upload);

        etTitle = findViewById(R.id.et_title);
        etDate = findViewById(R.id.et_date);
        etContent = findViewById(R.id.et_content);
        ivImage = findViewById(R.id.iv_img);
    }


    private String imgString() {
        String imageBase64 = null;
        if (selectedImageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                return getBase64FromBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageBase64;
    }

    // 工具内
    private String getBase64FromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}