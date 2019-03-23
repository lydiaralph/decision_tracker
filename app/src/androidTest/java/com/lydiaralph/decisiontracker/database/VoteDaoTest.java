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
import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class VoteDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private VoteDao mVoteDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mVoteDao = mDb.voteDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insertAndGetVote() throws Exception {
        Calendar date = Calendar.getInstance();
        Vote vote = new Vote(1, 2, 3, date);
        mVoteDao.insert(vote);
        List<Vote> allVotes = LiveDataTestUtil.getValue(mVoteDao.getAll());
        assertEquals(2, allVotes.get(0).getDecisionId());
        assertEquals(3, allVotes.get(0).getOptionId());
        assertEquals(date, allVotes.get(0).getVoteDate());
        assertNotNull(allVotes.get(0).getId());
    }

    @Test
    public void deleteAll() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(2019, 2, 1);
        Vote vote1 = new Vote(1, 2, 4, date);
        Vote vote2 = new Vote(2, 4, 5, date);

        mVoteDao.insert(vote1);
        mVoteDao.insert(vote2);
        mVoteDao.deleteAll();
        List<Vote> allVotes = LiveDataTestUtil.getValue(mVoteDao.getAll());
        assertTrue(allVotes.isEmpty());
    }
}
