package com.lydiaralph.decisiontracker.database.entity;

import java.time.LocalDate;

public class DateUtilsImpl implements DateUtils {

    private static DateUtilsImpl instance = null;

    private DateUtilsImpl(){
        // private: use getInstance()
    }

    public static DateUtilsImpl getInstance(){
        if(instance == null){
            instance = new DateUtilsImpl();
        }
        return instance;
    }

    public LocalDate getCurrentDate(){
        return LocalDate.now();
    }

}
