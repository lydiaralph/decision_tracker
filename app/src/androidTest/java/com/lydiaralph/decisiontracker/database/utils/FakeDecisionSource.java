package com.lydiaralph.decisiontracker.database.utils;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class FakeDecisionSource implements DecisionDao {

    private static FakeDecisionSource INSTANCE;

    private static final Map<Integer, Decision> DECISIONS_SERVICE_DATA = new LinkedHashMap<>();

    private static String FAKE_DECISION_TEXT_1 = "FAKE DECISION 1";
    public static String FAKE_DECISION_UPDATED_TITLE = "FAKE DECISION UPDATED TEXT";

    private FakeDecisionSource() {}

    public static FakeDecisionSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeDecisionSource();
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Decision>> getAll() {
        return null;
    }

    @Override
    public LiveData<Decision> getDecisionById(@NonNull Integer decisionId) {
        return null;
    }

    public void insert(@NonNull Decision decision) {
        DECISIONS_SERVICE_DATA.put(decision.getId(), decision);
    }

    @Override
    public void update(@NonNull Decision decision) {
        DECISIONS_SERVICE_DATA.put(decision.getId(), decision);
    }

    @Override
    public void deleteAll() {
        DECISIONS_SERVICE_DATA.clear();
    }

    @Override
    public void delete(@NonNull Decision decision) {

    }

    @VisibleForTesting
    public void addDecisions(Decision... decisions) {
        if (decisions != null) {
            for (Decision task : decisions) {
                DECISIONS_SERVICE_DATA.put(task.getId(), task);
            }
        }
    }

    public static Decision createAndFetchFakeDecision(String title, String desc) {
        return new Decision(title);
    }

    public static Decision fetchFakeDecision() {
        return new Decision(FAKE_DECISION_TEXT_1);
    }

    public static List<Decision> getFakeDecisions(int size) {
        List<Decision> decisionList = new ArrayList<Decision>();
        for(int i = 1; i <= size; i++ ) {
            Decision decision = new Decision("Decision " + i);
            decisionList.add(decision);
        }
        return decisionList;
    }
}