package ru.romanovAl.tochkatest.pagingRecyclerStuff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.paging.PagedListAdapter;

import ru.romanovAl.tochkatest.R;
import ru.romanovAl.tochkatest.activity.UserActivity;
import ru.romanovAl.tochkatest.databinding.PostLayoutBinding;
import ru.romanovAl.tochkatest.model.User;

public class UserAdapter extends PagedListAdapter<User, UserAdapter.UserViewHolder> {


    public UserAdapter() {
        super(USER_COMPARATOR);
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PostLayoutBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.post_layout,parent,false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(getItem(position));
    }



    public class UserViewHolder extends RecyclerView.ViewHolder {

        private PostLayoutBinding binding;

        public UserViewHolder(PostLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(final User currentUser) {
            binding.setUser(currentUser);
            binding.linkTextView.setOnClickListener(view -> {
                Uri address = Uri.parse(currentUser.getHtmlUrl());
                Intent browser = new Intent(Intent.ACTION_VIEW);
                browser.setData(address);
                view.getContext().startActivity(browser);
            });
            binding.postMainLayout.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), UserActivity.class);
                intent.putExtra("object", currentUser);
                view.getContext().startActivity(intent);
            });
        }
    }

    private static final DiffUtil.ItemCallback<User> USER_COMPARATOR = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            System.err.println("areItemsTheSame");
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            System.err.println("AreContentsTheSame");
            return oldItem.equals(newItem);
        }
    };
}



