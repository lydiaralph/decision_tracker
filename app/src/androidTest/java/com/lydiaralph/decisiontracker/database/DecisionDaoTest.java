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

import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.entity.DateUtils;
import com.lydiaralph.decisiontracker.database.entity.DateUtilsImpl;
import com.lydiaralph.decisiontracker.database.entity.Decision;
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

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DecisionDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DecisionDao mDecisionDao;
    private AppDatabase mDb;
    private DateUtils dateUtils = TestDateUtilsImpl.getInstance();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mDecisionDao = mDb.decisionDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insertAndGetDecisionText() throws Exception {
        Decision decision = new Decision(dateUtils, "decision");
        mDecisionDao.insert(decision);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(decision.getDecisionText(), allDecisions.get(0).getDecisionText());
    }

    @Test
    public void decisionById() throws Exception {
        Decision decision = new Decision(dateUtils, "decision");
        mDecisionDao.insert(decision);
        Decision fetchedDecision = LiveDataTestUtil.getValue(mDecisionDao.getDecisionById(1));
        assertEquals(decision.getDecisionText(), fetchedDecision.getDecisionText());
    }

    @Test
    public void insertDecisionWithCustomDates() throws Exception {
        LocalDate startDate = LocalDate.of(2019, 2, 1);
        LocalDate endDate = LocalDate.of(2019, 3, 2);
        Decision decision = new Decision("decision", startDate, endDate);
        mDecisionDao.insert(decision);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(decision.getDecisionText(), allDecisions.get(0).getDecisionText());
        assertEquals(0, allDecisions.get(0).getStartDate().compareTo(startDate));
        assertEquals(0, allDecisions.get(0).getEndDate().compareTo(endDate));
    }

    @Test
    public void decisionInsertedWithNoDatesHasDefaultDates() throws Exception {
        Decision decision = new Decision(dateUtils, "decision");
        mDecisionDao.insert(decision);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(decision.getDecisionText(), allDecisions.get(0).getDecisionText());
        assertEquals(TestDateUtilsImpl.MONTH, allDecisions.get(0).getStartDate().getMonthValue());
        assertEquals(dateUtils.getCurrentDate().getMonthValue() + 3,
                allDecisions.get(0).getEndDate().getMonthValue());
    }

    @Test
    public void getAllDecisions() throws Exception {
        Decision decision = new Decision(dateUtils, "aaa");
        mDecisionDao.insert(decision);
        Decision decision2 = new Decision(dateUtils, "bbb");
        mDecisionDao.insert(decision2);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(decision.getDecisionText(), allDecisions.get(0).getDecisionText());
        assertEquals(decision2.getDecisionText(), allDecisions.get(1).getDecisionText());
    }

    @Test
    public void deleteAll() throws Exception {
        Decision decision = new Decision(dateUtils, "decision");
        mDecisionDao.insert(decision);
        Decision decision2 = new Decision(dateUtils, "decision2");
        mDecisionDao.insert(decision2);
        mDecisionDao.deleteAll();
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertTrue(allDecisions.isEmpty());
    }
}
