package com.lydiaralph.decisiontracker;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void pageTitle() {
        onView(withId(R.id.page_title)).check(matches(withText(R.string.main_menu)));
    }

    @Test
    public void testViewResultsButton() {
        onView(withId(R.id.button_view_results)).check(matches(withText(R.string.view_results)));
        onView(withId(R.id.button_view_results)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.view_results)));
    }

    @Test
    public void testConfigureDecisionButton() {
        onView(withId(R.id.button_configure)).check(matches(withText(R.string.configure_decision)));
        onView(withId(R.id.button_configure)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.configure_decision)));
    }

}