package com.example.gerardus.newsapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.gerardus.newsapp.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository {

    private static NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mNewsItems;

    public Repository(Application application) {
        NewsItemDatabase db = NewsItemDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mNewsItems;
    }

    public static void syncNews() {
        new SyncNewsTask(mNewsItemDao).execute(NetworkUtils.buildUrl());
    }

    public static class SyncNewsTask extends AsyncTask<URL, Void, String> {
        private NewsItemDao mAsyncTaskDao;
        public static ArrayList<NewsItem> sNewsItems;

        SyncNewsTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAsyncTaskDao.deleteAll();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String newsSearchResults = "";
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String word) {
            super.onPostExecute(word);
            ArrayList<NewsItem> newsItemsParsed = JsonUtils.parseJSON(word);
            sNewsItems = newsItemsParsed;
            mAsyncTaskDao.insertNewsItem(newsItemsParsed);
        }
    }
}