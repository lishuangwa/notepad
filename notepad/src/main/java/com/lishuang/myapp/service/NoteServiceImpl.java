package com.lishuang.myapp.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lishuang.myapp.dao.Dao;
import com.lishuang.myapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteServiceImpl {


    public Long insertNote(Dao dao, Note note) {
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Dao.TITLE, note.getTitle());
        values.put(Dao.DATE, note.getDate());
        values.put(Dao.CONTENT, note.getContent());
        values.put(Dao.IMG, note.getImg());
        return db.insert(Dao.TABLE2, null, values);
    }

    @SuppressLint("Range")
    public List<Note> NoteList(Dao dao) {
        SQLiteDatabase db = dao.getReadableDatabase();
        List<Note> noteList = new ArrayList<>();
        //查询数据库中的数据
        Cursor cursor = db.query(Dao.TABLE2, null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            Note values;
            while (!cursor.isAfterLast()) {
                //实例化values对象
                values = new Note();
                //把数据库中的一个表中的数据赋值给values
                values.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(Dao.ID))));
                values.setTitle(cursor.getString(cursor.getColumnIndex(Dao.TITLE)));
                values.setDate(cursor.getString(cursor.getColumnIndex(Dao.DATE)));
                values.setContent(cursor.getString(cursor.getColumnIndex(Dao.CONTENT)));
                values.setImg(cursor.getString(cursor.getColumnIndex(Dao.IMG)));
                //将values对象存入list对象数组中
                noteList.add(values);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return noteList;
    }


    public Long deleteNote(Dao dao, Note note) {
        SQLiteDatabase db = dao.getWritableDatabase();
        int result = db.delete(Dao.TABLE2, Dao.TITLE + "=?", new String[]{String.valueOf(note.getTitle())});
        db.close();
        return (long) result;
    }


    public Long updateNote(Dao dao, Note note) {
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues values = new ContentValues();
        String title = note.getTitle();
        String date = note.getDate();
        String content = note.getContent();
        String img = note.getImg();
        values.put(Dao.TITLE, title);
        values.put(Dao.CONTENT, content);
        values.put(Dao.IMG, img);
        int update = db.update(Dao.TABLE2, values, Dao.DATE + "=?", new String[]{date});
        db.close();
        return (long) update;
    }
}
