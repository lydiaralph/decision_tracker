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
import com.lydiaralph.decisiontracker.database.dao.MoodDao;
import com.lydiaralph.decisiontracker.database.dao.MoodTypeDao;
import com.lydiaralph.decisiontracker.database.dao.OptionDao;
import com.lydiaralph.decisiontracker.database.dao.VoteDao;
import com.lydiaralph.decisiontracker.database.entity.DateUtils;
import com.lydiaralph.decisiontracker.database.entity.DateUtilsImpl;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.Mood;
import com.lydiaralph.decisiontracker.database.entity.MoodType;
import com.lydiaralph.decisiontracker.database.entity.Option;
import com.lydiaralph.decisiontracker.database.entity.Vote;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static java.lang.Math.toIntExact;

@Database(entities = {Decision.class, Option.class, Vote.class, MoodType.class, Mood.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DecisionDao decisionDao();
    public abstract VoteDao voteDao();
    public abstract OptionDao optionDao();
    public abstract MoodTypeDao moodTypeDao();
    public abstract MoodDao moodDao();

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
    // Currently used to insert test data. TODO: Remove most test data
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DecisionDao decisionDao;
        private final OptionDao optionDao;
        private final VoteDao voteDao;
        private final MoodTypeDao moodTypeDao;
        private final MoodDao moodDao;

        PopulateDbAsync(AppDatabase db) {
            decisionDao = db.decisionDao();
            optionDao = db.optionDao();
            voteDao = db.voteDao();
            moodTypeDao = db.moodTypeDao();
            moodDao = db.moodDao();
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
            LocalDate endDate = LocalDate.of(2019, 3, 5);
            Decision decisionWithOptionsNoVotes = new Decision("Decision with options and votes", startDate, endDate);
            Long decisionId = decisionDao.insert(decisionWithOptionsNoVotes);
            Long optionId1 = optionDao.insert(new Option(decisionId, "Option one"));
            Long optionId2 = optionDao.insert(new Option(decisionId, "Option two"));
            Long optionId3 = optionDao.insert(new Option(decisionId, "Option three"));
            Long optionId4 = optionDao.insert(new Option(decisionId, "Option four"));

            List<Long> optionsAvailable = Arrays.asList(optionId1, optionId2, optionId3, optionId4);

            // For each date, insert new Vote, picking random option, with random moods
            List<Mood> moodListToInsert = new ArrayList<>();
            int decisionDuration = ((Long) ChronoUnit.DAYS.between(startDate,endDate)).intValue();
            for(int i = 0; i < decisionDuration; i++){
                int optionIdToUse = (int)(Math.random() * ((optionsAvailable.size())));
                Long optionToVoteFor = optionsAvailable.get(optionIdToUse);

                long voteId = voteDao.insert(new Vote(optionToVoteFor, startDate.plus(i, ChronoUnit.DAYS)));

                moodListToInsert.addAll(Arrays.asList(
                        new Mood(voteId, 1, (int)(Math.random() * 100)), // Angry
                        new Mood(voteId, 2, (int)(Math.random() * 100)), // Sad
                        new Mood(voteId, 3, (int)(Math.random() * 100)), // Happy
                        new Mood(voteId, 4, (int)(Math.random() * 100)))); // Relaxed
            }

            moodDao.insertAll(moodListToInsert);
        }

        private void insertSampleDecision1(){
            LocalDate startDate = LocalDate.of(2019, 1, 3);
            LocalDate endDate = LocalDate.of(2019, 10, 13);
            Decision decisionWithOptionsNoVotes = new Decision("What shall I do next year?", startDate, endDate);
            Long decisionId = decisionDao.insert(decisionWithOptionsNoVotes);
            Long optionId1 = optionDao.insert(new Option(decisionId, "Train as a hairdresser"));
            Long optionId2 = optionDao.insert(new Option(decisionId, "Travel the world"));
            Long optionId3 = optionDao.insert(new Option(decisionId, "Move to Australia and become a surf instructor"));
            Long optionId4 = optionDao.insert(new Option(decisionId, "Watch loads of TV"));

            List<Long> optionsAvailable = Arrays.asList(optionId1, optionId2, optionId3, optionId4);

            // For each date, insert new Vote, picking random option, with random moods
            List<Mood> moodListToInsert = new ArrayList<>();
            int decisionDuration = ((Long) ChronoUnit.DAYS.between(startDate,endDate)).intValue();
            for(int i = 0; i < decisionDuration; i++){
                int optionIdToUse = (int)(Math.random() * ((optionsAvailable.size())));
                Long optionToVoteFor = optionsAvailable.get(optionIdToUse);

                long voteId = voteDao.insert(new Vote(optionToVoteFor, startDate.plus(i, ChronoUnit.DAYS)));

                moodListToInsert.addAll(Arrays.asList(
                        new Mood(voteId, 1, (int)(Math.random() * 100)), // Angry
                        new Mood(voteId, 2, (int)(Math.random() * 100)), // Sad
                        new Mood(voteId, 3, (int)(Math.random() * 100)), // Happy
                        new Mood(voteId, 4, (int)(Math.random() * 100)))); // Relaxed
            }

            moodDao.insertAll(moodListToInsert);
        }

        private void insertSampleDecision2(){
            LocalDate startDate = LocalDate.of(2019, 6, 4);
            LocalDate endDate = LocalDate.of(2019, 11, 6);
            Decision decisionWithOptionsNoVotes = new Decision("Baby names", startDate, endDate);
            Long decisionId = decisionDao.insert(decisionWithOptionsNoVotes);
            Long optionId1 = optionDao.insert(new Option(decisionId, "Meredith"));
            Long optionId2 = optionDao.insert(new Option(decisionId, "Alex"));
            Long optionId3 = optionDao.insert(new Option(decisionId, "Aubrey"));
            Long optionId4 = optionDao.insert(new Option(decisionId, "Ashley"));

            List<Long> optionsAvailable = Arrays.asList(optionId1, optionId2, optionId3, optionId4);

            // For each date up till today, insert new Vote, picking random option, with random moods
            List<Mood> moodListToInsert = new ArrayList<>();
            int decisionDuration = ((Long) ChronoUnit.DAYS.between(startDate, LocalDate.now())).intValue();
            for(int i = 0; i < decisionDuration; i++){
                int optionIdToUse = (int)(Math.random() * ((optionsAvailable.size())));
                Long optionToVoteFor = optionsAvailable.get(optionIdToUse);

                long voteId = voteDao.insert(new Vote(optionToVoteFor, startDate.plus(i, ChronoUnit.DAYS)));

                moodListToInsert.addAll(Arrays.asList(
                        new Mood(voteId, 1, (int)(Math.random() * 100)), // Angry
                        new Mood(voteId, 2, (int)(Math.random() * 100)), // Sad
                        new Mood(voteId, 3, (int)(Math.random() * 100)), // Happy
                        new Mood(voteId, 4, (int)(Math.random() * 100)))); // Relaxed
            }

            moodDao.insertAll(moodListToInsert);
        }

        private void insertSampleDecision3(){
            LocalDate startDate = LocalDate.of(2019, 1, 3);
            LocalDate endDate = LocalDate.of(2019, 1, 13);
            Decision decisionWithOptionsNoVotes = new Decision("Where shall I live?", startDate, endDate);
            Long decisionId = decisionDao.insert(decisionWithOptionsNoVotes);
            Long optionId1 = optionDao.insert(new Option(decisionId, "City"));
            Long optionId2 = optionDao.insert(new Option(decisionId, "Small town"));
            Long optionId3 = optionDao.insert(new Option(decisionId, "Countryside"));

            List<Long> optionsAvailable = Arrays.asList(optionId1, optionId2, optionId3);

            // For each date, insert new Vote, picking random option, with random moods
            List<Mood> moodListToInsert = new ArrayList<>();
            int decisionDuration = ((Long) ChronoUnit.DAYS.between(startDate,endDate)).intValue();
            for(int i = 0; i < decisionDuration; i++){
                int optionIdToUse = (int)(Math.random() * ((optionsAvailable.size())));
                Long optionToVoteFor = optionsAvailable.get(optionIdToUse);

                long voteId = voteDao.insert(new Vote(optionToVoteFor, startDate.plus(i, ChronoUnit.DAYS)));

                moodListToInsert.addAll(Arrays.asList(
                        new Mood(voteId, 1, (int)(Math.random() + 1 * 30)), // Angry
                        new Mood(voteId, 2, (int)(Math.random() + 1 * 40)), // Sad
                        new Mood(voteId, 3, (int)(Math.random() + 1 * 100)), // Happy
                        new Mood(voteId, 4, (int)(Math.random() + 1 * 100)))); // Relaxed
            }

            moodDao.insertAll(moodListToInsert);
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
            moodDao.deleteAll();
            insertDecisionWithOptionsNoVotes();
            insertDecisionWithOptionsAndVotes();
            insertSampleDecision1();
            insertSampleDecision2();
            insertSampleDecision3();

            insertStaticMoodTypes();

            return null;
        }
    }

}
