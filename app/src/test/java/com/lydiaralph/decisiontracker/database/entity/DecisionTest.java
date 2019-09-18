package com.lydiaralph.decisiontracker.database.entity;

import com.lydiaralph.decisiontracker.utils.TestDateUtilsImpl;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class DecisionTest {

    private TestDateUtilsImpl dateUtils;

    @Before
    public void setup(){
        dateUtils = TestDateUtilsImpl.getInstance();
    }

    @Test
    public void setDatesWithSpecifiedEndDate(){
        String decisionText = "Test 123";
        LocalDate endDate = LocalDate.of(TestDateUtilsImpl.YEAR,
                TestDateUtilsImpl.MONTH, TestDateUtilsImpl.DAY + 5);
        Decision decision = new Decision(dateUtils, decisionText, endDate);

        assertEquals("Test 123", decision.getDecisionText());
        assertEquals(LocalDate.of(TestDateUtilsImpl.YEAR,
                TestDateUtilsImpl.MONTH, TestDateUtilsImpl.DAY),
                decision.getStartDate());
        assertEquals(endDate,
                decision.getEndDate());
    }

    @Test
    public void datesAreSetByDefault(){
        String decisionText = "Test 123";
        Decision decision = new Decision(dateUtils, decisionText);

        assertEquals("Test 123", decision.getDecisionText());
        assertEquals(LocalDate.of(TestDateUtilsImpl.YEAR,
                TestDateUtilsImpl.MONTH, TestDateUtilsImpl.DAY),
                decision.getStartDate());
        assertEquals(LocalDate.of(TestDateUtilsImpl.YEAR,
                TestDateUtilsImpl.MONTH,
                TestDateUtilsImpl.DAY).plusDays(90),
                decision.getEndDate());
    }

    @Test
    public void datesCanBeSetDirectly(){
        String decisionText = "Test 123";
        LocalDate startDate = LocalDate.of(2018, 2, 5);
        LocalDate endDate = LocalDate.of(2019, 3, 2);

        Decision decision = new Decision(decisionText, startDate, endDate);

        assertEquals("Test 123", decision.getDecisionText());
        assertEquals(LocalDate.of(2018, 2, 5), decision.getStartDate());
        assertEquals(LocalDate.of(2019, 3, 2), decision.getEndDate());
    }

}