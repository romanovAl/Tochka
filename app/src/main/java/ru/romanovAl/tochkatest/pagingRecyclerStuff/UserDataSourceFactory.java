package ru.romanovAl.tochkatest.pagingRecyclerStuff;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import ru.romanovAl.tochkatest.model.User;

public class UserDataSourceFactory extends DataSource.Factory<Long, User> {

    public MutableLiveData<UserDataSource> userDataSourceMutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Long, User> create() {
        UserDataSource userDataSource = new UserDataSource();
        userDataSourceMutableLiveData.postValue(userDataSource);
        return userDataSource;
    }


}
