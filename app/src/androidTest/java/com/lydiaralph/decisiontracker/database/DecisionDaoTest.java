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
        assertEquals(allDecisions.get(0).getDecisionText(), decision.getDecisionText());
    }

    @Test
    public void getAllDecisions() throws Exception {
        Decision decision = new Decision("aaa");
        mDecisionDao.insert(decision);
        Decision decision2 = new Decision("bbb");
        mDecisionDao.insert(decision2);
        List<Decision> allDecisions = LiveDataTestUtil.getValue(mDecisionDao.getAll());
        assertEquals(allDecisions.get(0).getDecisionText(), decision.getDecisionText());
        assertEquals(allDecisions.get(1).getDecisionText(), decision2.getDecisionText());
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
