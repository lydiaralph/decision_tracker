package com.lydiaralph.decisiontracker.database.entity;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import androidx.room.TypeConverter;
import me.eugeniomarletti.kotlin.metadata.shadow.descriptors.EffectiveVisibility;

import static org.junit.Assert.assertEquals;

public class ConverterUtilsTest {

    private ConverterUtils underTest;

    @Before
    public void setup(){
        underTest = new ConverterUtils();
    }

    @Test
    public void testDateToTimestamp(){
        LocalDate localDate = LocalDate.of(2018, 3, 2);
        Long returnedValue = underTest.dateToTimestamp(localDate);
        assertEquals(new Long(1519948800), returnedValue);
    }

    @Test
    public void testFromTimestamp(){
        // Given
        Long timestamp = new Long(1612549735);

        // When
        LocalDate returnedValue = underTest.fromTimestamp(timestamp);

        // Then
        LocalDate expectedValue = LocalDate.of(2021, 2, 5);
        assertEquals(expectedValue, returnedValue);
    }




}
