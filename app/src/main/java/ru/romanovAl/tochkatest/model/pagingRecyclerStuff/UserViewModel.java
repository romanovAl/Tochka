package ru.romanovAl.tochkatest.model.pagingRecyclerStuff;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import ru.romanovAl.tochkatest.model.User;

public class UserViewModel extends ViewModel {

    public LiveData<PagedList<User>> userPagedList;

    private LiveData<UserDataSource> liveDataSource;

    public UserViewModel() {
        init();
    }

    private void init(){
        UserDataSourceFactory itemDataSourceFactory = new UserDataSourceFactory();
        liveDataSource = itemDataSourceFactory.userDataSourceMutableLiveData;
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(UserDataSource.PAGE_SIZE)
                .build();
        userPagedList = new LivePagedListBuilder<>(itemDataSourceFactory, config).build();
    }
}
