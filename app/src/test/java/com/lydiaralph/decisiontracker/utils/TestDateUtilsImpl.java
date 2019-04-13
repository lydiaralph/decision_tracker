package com.lydiaralph.decisiontracker.utils;

import com.lydiaralph.decisiontracker.database.entity.DateUtils;
import com.lydiaralph.decisiontracker.database.entity.DateUtilsImpl;

import java.time.LocalDate;

public class TestDateUtilsImpl implements DateUtils {

    public static final int YEAR = 2019;
    public static final int MONTH = 3;
    public static final int DAY = 12;

    private static TestDateUtilsImpl instance = null;

    private TestDateUtilsImpl(){
        // private: use getInstance()
    }

    public static TestDateUtilsImpl getInstance(){
        if(instance == null){
            instance = new TestDateUtilsImpl();
        }
        return instance;
    }

    public final LocalDate getCurrentDate(){
        return LocalDate.of(YEAR, MONTH, DAY);
    }
}
