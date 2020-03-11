package ru.romanovAl.tochkatest.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.romanovAl.tochkatest.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView name, id, link, score;

    public ImageView avatar;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_TextView);
        id = itemView.findViewById(R.id.id_TextView);
        link = itemView.findViewById(R.id.link_TextView);
        score = itemView.findViewById(R.id.score_textView);
        avatar = itemView.findViewById(R.id.avatar_imageView);


    }
}
