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

import android.content.Context;
import android.os.AsyncTask;

import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.dao.MoodTypeDao;
import com.lydiaralph.decisiontracker.database.dao.OptionDao;
import com.lydiaralph.decisiontracker.database.dao.VoteDao;
import com.lydiaralph.decisiontracker.database.entity.DateUtils;
import com.lydiaralph.decisiontracker.database.entity.DateUtilsImpl;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.MoodType;
import com.lydiaralph.decisiontracker.database.entity.Option;
import com.lydiaralph.decisiontracker.database.entity.Vote;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static java.lang.Math.toIntExact;

@Database(entities = {Decision.class, Option.class, Vote.class, MoodType.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DecisionDao decisionDao();
    public abstract VoteDao voteDao();
    public abstract OptionDao optionDao();
    public abstract MoodTypeDao moodTypeDao();

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

        private final DecisionDao decisionDao;
        private final OptionDao optionDao;
        private final VoteDao voteDao;
        private final MoodTypeDao moodTypeDao;

        private final DateUtils dateUtils;

        PopulateDbAsync(AppDatabase db) {
            decisionDao = db.decisionDao();
            optionDao = db.optionDao();
            voteDao = db.voteDao();
            moodTypeDao = db.moodTypeDao();
            dateUtils = DateUtilsImpl.getInstance();
        }

        private void insertDecisionWithOptionsNoVotes(){
            LocalDate startDate = LocalDate.of(2019, 2, 1);
            LocalDate endDate = LocalDate.of(2019, 2, 5);
            Decision decisionWithOptionsNoVotes = new Decision("Decision with options no votes", startDate, endDate);
            long decisionId = decisionDao.insert(decisionWithOptionsNoVotes);
            Option option = new Option(decisionId, "First option");
            optionDao.insert(option);
            Option option2 = new Option(decisionId, "Second option");
            optionDao.insert(option2);
            Option option3 = new Option(decisionId, "Third option");
            optionDao.insert(option3);
        }

        private void insertDecisionWithOptionsAndVotes(){
            LocalDate startDate = LocalDate.of(2019, 2, 1);
            LocalDate endDate = LocalDate.of(2019, 2, 5);
            Decision decisionWithOptionsNoVotes = new Decision("Decision with options and votes", startDate, endDate);
            long decisionId = decisionDao.insert(decisionWithOptionsNoVotes);
            long optionId1 = optionDao.insert(new Option(decisionId, "Option with three votes"));
            voteDao.insert(new Vote(optionId1, LocalDate.now()));
            voteDao.insert(new Vote(optionId1, LocalDate.now()));
            voteDao.insert(new Vote(optionId1, LocalDate.now()));

            optionDao.insert(new Option(decisionId, "Option with no votes"));

            long optionId3 = optionDao.insert(new Option(decisionId, "Option with one vote"));
            voteDao.insert(new Vote(optionId3, LocalDate.now()));
        }

        private void insertStaticMoodTypes(){
            List<MoodType> moodTypes = Arrays.asList(
                    new MoodType(1, "Angry"),
                    new MoodType(2, "Sad"),
                    new MoodType(3, "Happy"),
                    new MoodType(4, "Relaxed"));

            moodTypeDao.insertAll(moodTypes);
        }

        @Override
        protected Void doInBackground(final Void... params) {
            decisionDao.deleteAll();
            Decision decisionWithNoOptions = new Decision(dateUtils, "Decision with no options");
            decisionDao.insert(decisionWithNoOptions);

            insertDecisionWithOptionsNoVotes();
            insertDecisionWithOptionsAndVotes();

            insertStaticMoodTypes();

            return null;
        }
    }

}
