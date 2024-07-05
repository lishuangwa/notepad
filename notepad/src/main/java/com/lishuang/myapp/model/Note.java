package com.lishuang.myapp.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Note {
    private Integer id;
    private String title;
    private String date;
    private String content;
    private String img;

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Note(String title, String date, String content, String img) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.img = img;
    }

    public Note() {
    }

    public Note(Integer id, String title, String date, String content, String img) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.img = img;
    }
}
