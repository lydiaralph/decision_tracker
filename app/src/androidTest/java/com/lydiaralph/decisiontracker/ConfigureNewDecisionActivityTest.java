package com.lydiaralph.decisiontracker;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ConfigureNewDecisionActivityTest {

    @Rule
    public ActivityTestRule<ConfigureNewDecisionActivity> activityTestRule =
            new ActivityTestRule<>(ConfigureNewDecisionActivity.class);

    @Test
    public void pageTitle() {
        onView(withId(R.id.page_title)).check(matches(withText(R.string.configure_decision)));
    }

    @Test
    public void testConfigureDecisionButton() {
        onView(withId(R.id.button_return_to_main_menu)).check(matches(withText(R.string.return_to_main_menu)));
        onView(withId(R.id.button_return_to_main_menu)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.main_menu)));
    }

}