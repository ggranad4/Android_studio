package com.example.gerardus.newsapp;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.app.Application;
import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<NewsItem>> mNewsItems;

    public NewsItemViewModel(Application application) {
        super(application);
        mRepository = new Repository(application) {
            @Override
            public LiveData<List<NewsItem>> getAllNewsItems() {
                return super.getAllNewsItems();
            }
        };
        mNewsItems = mRepository.getAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mNewsItems;
    }

    public void syncNews() {
        Repository.syncNews();
    }
}