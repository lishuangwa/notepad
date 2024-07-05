package com.lishuang.myapp.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lishuang.myapp.MainActivity;
import com.lishuang.myapp.R;
import com.lishuang.myapp.ShowActivity;
import com.lishuang.myapp.dao.Dao;
import com.lishuang.myapp.model.Note;
import com.lishuang.myapp.service.NoteServiceImpl;
import com.lishuang.myapp.utils.BitmapToString;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Dao dao;
    private NoteServiceImpl noteService;
    private List<Note> noteList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Item_title;
        TextView Item_date;
        TextView Item_content;
        ImageView Item_img;
        View ItemView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemView = itemView;
            Item_title = itemView.findViewById(R.id.item_title);
            Item_date = itemView.findViewById(R.id.item_date);
            Item_content = itemView.findViewById(R.id.item_content);
            Item_img = itemView.findViewById(R.id.item_img);
        }

    }

    public RecyclerViewAdapter(List<Note> noteList, Dao dao) {
        this.noteList = noteList;
        this.dao = dao;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

//         单击查看详情
        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                Note data = noteList.get(position);
                Intent intent = new Intent(v.getContext(), ShowActivity.class);
                intent.putExtra("title", data.getTitle());
                intent.putExtra("content", data.getContent());
                intent.putExtra("date", data.getDate());
                intent.putExtra("img", data.getImg());
                v.getContext().startActivity(intent);
            }
        });
//
        // 长按删除
        holder.ItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = holder.getLayoutPosition();
                Note data = noteList.get(position);
                // todo : 我需要一个弹框提示我是否删除
                Context context = view.getContext();
                new AlertDialog.Builder(context)
                        .setTitle("提示框")
                        .setMessage("是否删除?")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

//                                        // 删除操作
                                        Dao database = dao;
                                        noteService = new NoteServiceImpl();
                                        Long result = noteService.deleteNote(database, data);
                                        if (result > 0) {
                                            noteList.remove(position);
                                            notifyItemRemoved(position);
                                            Toast.makeText(context, "删除成功!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        .setNegativeButton("no", null)
                        .show();
                return true;
            }
        });

//         view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getLayoutPosition();
//                Note data = noteList.get(position);
//                Toast.makeText(v.getContext(), "click" + data.getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.Item_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "click 图片", Toast.LENGTH_SHORT).show();
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.Item_title.setText(note.getTitle());
        holder.Item_date.setText(note.getDate());
        holder.Item_content.setText(note.getContent());
        Bitmap bitmapFromBase64 = BitmapToString.getBitmapFromBase64(note.getImg());
        holder.Item_img.setImageBitmap(bitmapFromBase64);
    }


    @Override
    public int getItemCount() {
        if (noteList != null) {
            return noteList.size();
        }
        return 0;
    }
}
