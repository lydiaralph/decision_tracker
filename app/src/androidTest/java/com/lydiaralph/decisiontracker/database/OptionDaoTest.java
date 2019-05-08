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
 * Modified: 'DecisionDao' rather than 'WordDao'.
 */

import android.content.Context;

import com.lydiaralph.decisiontracker.database.dao.OptionDao;
import com.lydiaralph.decisiontracker.database.entity.DateUtilsImpl;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.Option;
import com.lydiaralph.decisiontracker.database.utils.LiveDataTestUtil;
import com.lydiaralph.decisiontracker.database.utils.TestDateUtilsImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static java.lang.Math.toIntExact;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class OptionDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private OptionDao mOptionDao;
    private AppDatabase mDb;
    Integer decisionId;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mOptionDao = mDb.optionDao();

        Decision decision = new Decision(TestDateUtilsImpl.getInstance(), "test");
        decisionId = toIntExact(mDb.decisionDao().insert(decision));
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insertAndGetOption() throws Exception {
        Option option = new Option(1, decisionId, "new option");
        mOptionDao.insert(option);
        List<Option> allOptions = LiveDataTestUtil.getValue(mOptionDao.getAllByDecisionId(decisionId));
        assertEquals(option.getOptionText(), allOptions.get(0).getOptionText());
        assertEquals(option.getDecisionId(), allOptions.get(0).getDecisionId());
    }

    @Test
    public void insertAndGetAllOptions() throws Exception {
        Option option1 = new Option(1, decisionId, "new option");
        Option option2 = new Option(2, decisionId, "second option");
        mOptionDao.insertAll(option1, option2);
        List<Option> allOptions = LiveDataTestUtil.getValue(mOptionDao.getAll());
        assertEquals(option1.getOptionText(), allOptions.get(0).getOptionText());
        assertEquals(option1.getDecisionId(), allOptions.get(0).getDecisionId());
        assertEquals(option2.getOptionText(), allOptions.get(1).getOptionText());
        assertEquals(option2.getDecisionId(), allOptions.get(1).getDecisionId());
    }

    @Test
    public void deleteOption() throws Exception {
        Option option1 = new Option(1, decisionId, "new option");
        mOptionDao.insert(option1);
        List<Option> fetched = LiveDataTestUtil.getValue(mOptionDao.getAll());
        assert(fetched.size() > 0);

        mOptionDao.delete(option1);
        fetched = LiveDataTestUtil.getValue(mOptionDao.getAll());
        assert(fetched.isEmpty());
    }

}
