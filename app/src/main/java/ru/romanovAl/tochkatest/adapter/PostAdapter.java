package ru.romanovAl.tochkatest.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import retrofit2.http.Url;
import ru.romanovAl.tochkatest.R;
import ru.romanovAl.tochkatest.model.User;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    Context context;
    List<User> userList;

    public PostAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_layout, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        final User currentUser = userList.get(position);

        holder.name.setText(currentUser.getLogin());
        holder.id.setText("id - " + currentUser.getId());
        holder.link.setText("Перейти на страницу");
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri address = Uri.parse(currentUser.getHtmlUrl());
                Intent browser = new Intent(Intent.ACTION_VIEW);
                browser.setData(address);
                view.getContext().startActivity(browser);
            }
        });
        holder.link.setTag(holder.link.getKeyListener());
        holder.link.setKeyListener(null);
        holder.score.setText("Рейтинг - " + currentUser.getScore());

        Picasso
                .get()
                .load(userList.get(position).getAvatarUrl())
                .resize(400,400)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
