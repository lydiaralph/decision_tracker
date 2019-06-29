package com.lydiaralph.decisiontracker.activities;

import android.content.Intent;

import com.lydiaralph.decisiontracker.R;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@Ignore("TODO: waiting for mock database work")
public class ViewDecisionsDetailActivityTest {

    @Rule
    public ActivityTestRule<ViewDecisionDetailActivity> activityTestRule =
            new ActivityTestRule<ViewDecisionDetailActivity>(ViewDecisionDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra(ViewDecisionsCategoryActivity.VIEW_DECISION_ID, 1);
                    return intent;
                }
            };

    @Test
    public void pageTitle() {
        onView(withId(R.id.page_title)).check(matches(withText(R.string.view_results)));
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