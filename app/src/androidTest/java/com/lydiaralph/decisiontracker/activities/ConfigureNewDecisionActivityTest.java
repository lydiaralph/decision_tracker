package com.lydiaralph.decisiontracker.activities;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.activities.ConfigureNewDecisionActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ConfigureNewDecisionActivityTest {

    @Rule
    public ActivityTestRule<ConfigureNewDecisionActivity> activityTestRule =
            new ActivityTestRule<>(ConfigureNewDecisionActivity.class);

    @Test
    public void pageTitle() {
        onView(ViewMatchers.withId(R.id.page_title)).check(matches(withText(R.string.configure_decision)));
    }

    @Test
    public void testMainMenuButton() {
        onView(withId(R.id.button_return_to_main_menu)).check(matches(withText(R.string.return_to_main_menu)));
        onView(withId(R.id.button_return_to_main_menu)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.main_menu)));
    }

    @Test
    public void testConfigureNewDecisionButton() {
        onView(withId(R.id.page_title)).check(matches(withText(R.string.configure_decision)));
        onView(withId(R.id.button_submit_new_decision)).check(matches(withText(R.string.save)));
        onView(withId(R.id.button_submit_new_decision)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.view_results)));
    }

    @Test
    public void testDecisionInputFields() {
        onView(withId(R.id.prompt_decision_text)).check(matches(withText(R.string.ask_this_question)));

        onView(withId(R.id.radio_day)).check(matches(withText(R.string.days)));
        onView(withId(R.id.radio_week)).check(matches(withText(R.string.weeks)));
        onView(withId(R.id.radio_month)).check(matches(withText(R.string.months)));
    }


}