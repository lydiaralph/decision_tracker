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
 * Modified: 'VoteDao' rather than 'WordDao'.
 */

import android.content.Context;

import com.lydiaralph.decisiontracker.database.dao.VoteDao;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.Option;
import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.utils.LiveDataTestUtil;
import com.lydiaralph.decisiontracker.database.utils.TestDateUtilsImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static java.lang.Math.toIntExact;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class VoteDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private VoteDao mVoteDao;
    private AppDatabase mDb;
    Integer optionId;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mVoteDao = mDb.voteDao();

        Decision decision = new Decision(TestDateUtilsImpl.getInstance(), "test");
        Integer decisionId = toIntExact(mDb.decisionDao().insert(decision));

        Option option = new Option(1, decisionId, "new option");
        optionId = toIntExact(mDb.optionDao().insert(option));
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insertAndGetVote() throws Exception {
        LocalDate date = LocalDate.now();
        Vote vote = new Vote(optionId, date);
        mVoteDao.insert(vote);
        List<Vote> allVotes = LiveDataTestUtil.getValue(mVoteDao.getAll());
        assertEquals(optionId.intValue(), allVotes.get(0).getOptionId());
        assertEquals(date, allVotes.get(0).getVoteDate());
    }

    @Test
    public void deleteAll() throws Exception {
        LocalDate date = LocalDate.of(2019, 2, 1);
        Vote vote1 = new Vote(optionId, date);
        Vote vote2 = new Vote(optionId, date);

        mVoteDao.insert(vote1);
        mVoteDao.insert(vote2);
        mVoteDao.deleteAll();
        List<Vote> allVotes = LiveDataTestUtil.getValue(mVoteDao.getAll());
        assertTrue(allVotes.isEmpty());
    }
}
