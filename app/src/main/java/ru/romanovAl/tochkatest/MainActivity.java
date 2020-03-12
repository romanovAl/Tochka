package ru.romanovAl.tochkatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.romanovAl.tochkatest.Api.GithubRxApi;
import ru.romanovAl.tochkatest.adapter.PostAdapter;
import ru.romanovAl.tochkatest.model.CustomAlertDialog;
import ru.romanovAl.tochkatest.model.Item;
import ru.romanovAl.tochkatest.model.autoLoadingRecyclerStuff.PaginationScrollListener;

import static ru.romanovAl.tochkatest.model.autoLoadingRecyclerStuff.PaginationScrollListener.PAGE_START;

public class MainActivity extends AppCompatActivity {

    private GithubRxApi githubRxApi;

    private Item currentItem;

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TextView helloTextView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ConstraintLayout mainLayout;

    private PostAdapter postAdapter;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler);
        helloTextView = findViewById(R.id.hello_textView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        progressBar.setVisibility(View.INVISIBLE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });

        githubRxApi = new GithubRxApi();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomAlertDialog customAlertDialog = new CustomAlertDialog(MainActivity.this);


                customAlertDialog.setCancelable(true);
                customAlertDialog.show();

                customAlertDialog.setButtonSearchOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doSearch(customAlertDialog.getEditTextSearchText());

                        customAlertDialog.dismiss();
                    }
                });

                customAlertDialog.setButtonCancelOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customAlertDialog.dismiss();
                    }
                });

                customAlertDialog.setEditTextOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });

        recyclerView.addOnScrollListener(new PaginationScrollListener() {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                //doApiCall
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    private String userName;

    private void doSearch(String userName){
        githubRxApi.getUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Item>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        helloTextView.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(Item item) {
                        currentItem = item;
                    }

                    @Override
                    public void onError(Throwable e) {
                        helloTextView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        postAdapter = new PostAdapter(MainActivity.this,currentItem.getItems());
                        recyclerView.setAdapter(postAdapter);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                });
    }

    @Override
    public void onBackPressed() {
        if(recyclerView.getVisibility() == View.VISIBLE){
            recyclerView.setVisibility(View.INVISIBLE);
            helloTextView.setVisibility(View.VISIBLE);
        }
        else{
            super.onBackPressed();
        }
    }
}
