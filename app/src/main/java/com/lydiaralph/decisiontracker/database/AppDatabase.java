package com.lydiaralph.decisiontracker.database;

/*
 * Copyright (C) 2019 Lydia Ralph
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Derived from https://github.com/googlecodelabs/android-room-with-a-view
 *
 * Modified: 'AppDatabase' rather than 'WordRoomDatabase'. Migrated to AndroidX.
 */

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.Calendar;
import java.util.Date;

@Database(entities = {Decision.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DecisionDao decisionDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    // To populate the database with static data
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DecisionDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.decisionDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Decision decision = new Decision("New decision");
            mDao.insert(decision);
            Calendar startDate = Calendar.getInstance();
            startDate.set(2019, 2, 1);
            Calendar endDate = Calendar.getInstance();
            endDate.set(2019, 3, 2);
            decision = new Decision("Second decision");
            mDao.insert(decision);
            return null;
        }
    }

}
