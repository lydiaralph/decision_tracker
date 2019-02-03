package com.lydiaralph.decisiontracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.view.Menu;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MenuBasedActivityTest {
    @Rule
    public ActivityTestRule<MenuBasedActivity> activityTestRule = new ActivityTestRule<>(MenuBasedActivity.class);

    @Test
    public void testSideMenu() {
        onView(withId(R.id.button_view_results)).check(matches(withText(R.string.view_results)));
        onView(withId(R.id.button_configure)).check(matches(withText(R.string.configure_decision)));
        onView(withId(R.id.button_return_to_main_menu)).check(matches(withText(R.string.return_to_main_menu)));

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.view_results)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.view_results)));

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.configure_decision)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.configure_decision)));

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.return_to_main_menu)).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.main_menu)));
    }
}