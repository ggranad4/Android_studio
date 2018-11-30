package com.example.gerardus.newsapp;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsItemDatabase extends RoomDatabase {
    public abstract NewsItemDao newsItemDao();
    private static volatile NewsItemDatabase sInstance;

    static NewsItemDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (NewsItemDatabase.class) {
                if (sInstance == null) {
                    // Create database here
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NewsItemDatabase.class, "news_item_Database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return sInstance;
    }
}