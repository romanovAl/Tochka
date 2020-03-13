package ru.romanovAl.tochkatest.model.pagingRecyclerStuff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.paging.PagedListAdapter;

import com.squareup.picasso.Picasso;

import ru.romanovAl.tochkatest.R;
import ru.romanovAl.tochkatest.model.User;

public class UserAdapter extends PagedListAdapter<User, UserAdapter.UserViewHolder> {
    public UserAdapter() {
        super(USER_COMPARATOR);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView name, id, link, score;

        public ImageView avatar;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_TextView);
            id = itemView.findViewById(R.id.id_TextView);
            link = itemView.findViewById(R.id.link_TextView);
            score = itemView.findViewById(R.id.score_textView);
            avatar = itemView.findViewById(R.id.avatar_imageView);

        }

        void bind(final User currentUser) {

            name.setText(currentUser.getLogin());
            id.setText("id - " + currentUser.getId());

            link.setText("Перейти на страницу");
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri address = Uri.parse(currentUser.getHtmlUrl());
                    Intent browser = new Intent(Intent.ACTION_VIEW);
                    browser.setData(address);
                    view.getContext().startActivity(browser);
                }
            });
            link.setKeyListener(null);

            score.setText("Рейтинг - " + currentUser.getScore());

            Picasso
                    .get()
                    .load(currentUser.getAvatarUrl())
                    .resize(400, 400)
                    .centerCrop()
                    .error(R.drawable.error_avatar)
                    .placeholder(R.drawable.download)
                    .into(avatar);
        }
    }

    private static final DiffUtil.ItemCallback<User> USER_COMPARATOR = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem == newItem;
        }
    };
}



