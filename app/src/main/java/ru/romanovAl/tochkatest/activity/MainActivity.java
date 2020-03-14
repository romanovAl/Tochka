package ru.romanovAl.tochkatest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import ru.romanovAl.tochkatest.R;
import ru.romanovAl.tochkatest.model.CustomAlertDialog;
import ru.romanovAl.tochkatest.pagingRecyclerStuff.UserAdapter;
import ru.romanovAl.tochkatest.pagingRecyclerStuff.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private FloatingActionButton fab;
    private static TextView helloTextView;
    private static ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static String userName;

    public static String getUserName() {
        return userName;
    }

    private UserViewModel userViewModel;

    private UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler);
        helloTextView = findViewById(R.id.hello_textView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        progressBar.setVisibility(View.INVISIBLE);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            ///TODO сделать обновление
            swipeRefreshLayout.setRefreshing(false);
        },1000));

        fab.setOnClickListener(view -> {
            final CustomAlertDialog customAlertDialog = new CustomAlertDialog(MainActivity.this);

            customAlertDialog.setCancelable(true);
            customAlertDialog.show();

            customAlertDialog.setButtonSearchOnClick(view1 -> {
                userName = customAlertDialog.getEditTextSearchText();
                setBarVisibility(true);
                setHelloTextVisibility(false);
                initRecycler();
                fab.hide();

                customAlertDialog.dismiss();

            });

            customAlertDialog.setButtonCancelOnClick(view13 -> {

            });

            customAlertDialog.setEditTextOnClick(view12 -> {

            });
        });

    }

    private void initRecycler(){
        final UserAdapter userAdapter = new UserAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.userPagedList.observe(this, users -> userAdapter.submitList(users));

        recyclerView.setAdapter(userAdapter);
    }


    public static void setRecyclerVisibility(boolean code) {
        if(code){
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    public static void setHelloTextVisibility(boolean code) {
        if(code){
            helloTextView.setVisibility(View.VISIBLE);
        }else{
            helloTextView.setVisibility(View.INVISIBLE);
        }
    }

    public static void setBarVisibility(boolean code) {
        if(code){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    @BindingAdapter({"url", "errorImage"})
    public static void loadImage(ImageView view, String url, Drawable errorDrawable){
        Picasso
                .get()
                .load(url)
                .resize(400,400)
                .error(errorDrawable)
                .placeholder(R.drawable.download)
                .into(view);
    }



}
