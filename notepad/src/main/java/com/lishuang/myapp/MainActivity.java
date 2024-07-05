package com.lishuang.myapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lishuang.myapp.adapter.RecyclerViewAdapter;
import com.lishuang.myapp.dao.Dao;
import com.lishuang.myapp.model.Note;
import com.lishuang.myapp.service.NoteServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Dao dao;
    private NoteServiceImpl noteService;
    private Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new Dao(this);
        initView();

        // 数据+recycleView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        noteService = new NoteServiceImpl();
        List<Note> noteList = noteService.NoteList(dao);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(noteList, dao);
        recyclerView.setAdapter(recyclerViewAdapter);

        // 添加按钮
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 页面跳转
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        addBtn = findViewById(R.id.btn_add);
    }

    public Dao getDatabase() {
        return new Dao(this);
    }
}


