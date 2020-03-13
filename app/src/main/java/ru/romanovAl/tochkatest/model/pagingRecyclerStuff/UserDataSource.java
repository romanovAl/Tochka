package ru.romanovAl.tochkatest.model.pagingRecyclerStuff;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.romanovAl.tochkatest.Api.GithubRxApi;
import ru.romanovAl.tochkatest.model.Item;
import ru.romanovAl.tochkatest.model.User;

public class UserDataSource extends PageKeyedDataSource<Long, User> {

    public static int PAGE_SIZE = 10;

    public static long FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, User> callback) {


        GithubRxApi githubRxApi = new GithubRxApi();

        githubRxApi.getUserWithPageParam("tom",FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Item>() {
                    List<User> userList;
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Item item) {
                        userList = item.getItems();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        callback.onResult(userList, null, FIRST_PAGE + 1);
                    }
                });

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Long>  params,
                           @NonNull final LoadCallback<Long, User> callback) {

        GithubRxApi githubRxApi = new GithubRxApi();

        githubRxApi.getUserWithPageParam("tom",params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Item>() {
                    List<User> userList;
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Item item) {
                        userList = item.getItems();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        long key;
                        if (params.key > 1) {
                            key = params.key - 1;
                        } else {
                            key = 0;
                        }
                        callback.onResult(userList, key);
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, User> callback) {
        GithubRxApi githubRxApi = new GithubRxApi();

        githubRxApi.getUserWithPageParam("tom", params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Item>() {
                    List<User> userList;
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Item item) {
                        userList = item.getItems();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        callback.onResult(userList,params.key + 1);
                    }
                });
    }
}
