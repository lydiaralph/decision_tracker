package com.lydiaralph.decisiontracker.database;

import android.app.Application;
import android.content.Context;

import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.entity.Decision;
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
import androidx.test.InstrumentationRegistry;
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
        Decision decision = new Decision("decision");
        mDecisionDao.insert(decision);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(decision.getDecisionText(), allDecisions.get(0).getDecisionText());
    }

    @Test
    public void insertDecisionWithCustomDates() throws Exception {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2019, 2, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 3, 2);
        Decision decision = new Decision("decision", startDate, endDate);
        mDecisionDao.insert(decision);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(decision.getDecisionText(), allDecisions.get(0).getDecisionText());
        assertEquals(0, allDecisions.get(0).getStartDate().compareTo(startDate));
        assertEquals(0, allDecisions.get(0).getEndDate().compareTo(endDate));
    }

    @Test
    public void decisionInsertedWithNoDatesHasDefaultDates() throws Exception {
        Decision decision = new Decision("decision");
        mDecisionDao.insert(decision);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(decision.getDecisionText(), allDecisions.get(0).getDecisionText());
        assertEquals(Calendar.getInstance().get(Calendar.MONTH), allDecisions.get(0).getStartDate().get(Calendar.MONTH));
        assertEquals(Calendar.getInstance().get(Calendar.MONTH) + 3, allDecisions.get(0).getEndDate().get(Calendar.MONTH));
    }

    @Test
    public void getAllDecisions() throws Exception {
        Decision decision = new Decision("aaa");
        mDecisionDao.insert(decision);
        Decision decision2 = new Decision("bbb");
        mDecisionDao.insert(decision2);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(decision.getDecisionText(), allDecisions.get(0).getDecisionText());
        assertEquals(decision2.getDecisionText(), allDecisions.get(1).getDecisionText());
    }

    @Test
    public void deleteAll() throws Exception {
        Decision decision = new Decision("decision");
        mDecisionDao.insert(decision);
        Decision decision2 = new Decision("decision2");
        mDecisionDao.insert(decision2);
        mDecisionDao.deleteAll();
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertTrue(allDecisions.isEmpty());
    }
}
