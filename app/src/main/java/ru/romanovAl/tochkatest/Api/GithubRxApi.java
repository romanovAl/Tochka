package ru.romanovAl.tochkatest.Api;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.romanovAl.tochkatest.model.Item;

public class GithubRxApi {

    private ApiService apiService;

    private final static String BASE_URL = "https://api.github.com/";

    public GithubRxApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public Observable<Item> getUser(String userName) {
        return apiService.getUsers(userName);
    }

    public Observable<Item> getUserWithPageParam(String userName, long pageNum){
        return apiService.getUsersWithPageParam(userName,pageNum);
    }

}
