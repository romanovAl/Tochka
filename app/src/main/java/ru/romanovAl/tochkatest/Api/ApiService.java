package ru.romanovAl.tochkatest.Api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.romanovAl.tochkatest.model.Item;

public interface ApiService {

    @GET("search/users")
    Observable<Item> getUsers(@Query("q") String user);

}
