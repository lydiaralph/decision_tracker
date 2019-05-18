package com.lydiaralph.decisiontracker.activities;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.Option;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;
import com.lydiaralph.decisiontracker.database.utils.TestDateUtilsImpl;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModelFactory;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ViewDecisionsDetailActivityTest {

    @Test
    public void pageTitle() {
        onView(ViewMatchers.withId(R.id.page_title)).check(matches(withText(R.string.view_results)));
    }
    @Test
    public void testMainMenuButton() {
        onView(withId(R.id.button_return_to_main_menu)).check(matches(withText(R.string.return_to_main_menu)));
        onView(withId(R.id.button_return_to_main_menu)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.main_menu_title)));
    }

    @Test
    public void testEditorialTextIsVisible(){
        // TODO
    }

    @Test
    public void testDatesView(){
        // TODO
    }

    @Test
    public void testWithEmptyOptionList(){
        // TODO
    }

    @Test
    public void testWithEmptyVotes(){
        // TODO
    }

    @Test
    public void testResultsAreDisplayedWithPieChart(){
        // TODO
    }


}