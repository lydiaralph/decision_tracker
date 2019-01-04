package com.lydiaralph.decisiontracker.database;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract User userDao();
}
