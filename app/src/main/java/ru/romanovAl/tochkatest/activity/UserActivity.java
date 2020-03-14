package ru.romanovAl.tochkatest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.romanovAl.tochkatest.R;
import ru.romanovAl.tochkatest.databinding.ActivityUserBinding;
import ru.romanovAl.tochkatest.model.User;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = (User)getIntent().getSerializableExtra("object");
        ActivityUserBinding activityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        activityUserBinding.setUser(user);

        activityUserBinding.userActivityButton.setText("Перейти на страницу");
        activityUserBinding.userActivityButton.setOnClickListener(view -> {

            Uri address = Uri.parse(user.getHtmlUrl());
            Intent browser = new Intent(Intent.ACTION_VIEW);
            browser.setData(address);
            startActivity(browser);

        });
    }


}


