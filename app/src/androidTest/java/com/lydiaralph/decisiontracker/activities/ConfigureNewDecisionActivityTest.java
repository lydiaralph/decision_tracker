package com.lydiaralph.decisiontracker.activities;

import android.widget.DatePicker;

import com.lydiaralph.decisiontracker.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;

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
        onView(withId(R.id.button_return_to_main_menu)).perform(closeSoftKeyboard()).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText(R.string.main_menu_title)));
    }

    @Test
    public void testDecisionInputFields() {
        onView(withId(R.id.prompt_decision_text)).check(matches(withText(R.string.ask_this_question)));
    }

    @Test
    public void withPopulatedFieldsShouldLoadSuccess() throws InterruptedException {
        // Given
        onView(withId(R.id.input_decision_text)).perform(typeText("Decision Text"));
        onView(withId(R.id.input_option_1)).perform(typeText("Option 1"));
        onView(withId(R.id.input_option_2)).perform(typeText("Option 2")).perform(closeSoftKeyboard());
        Thread.sleep(250);

        // When
        onView(withId(R.id.button_submit_new_decision)).perform(closeSoftKeyboard()).perform(click());

        // Then
        onView(withId(R.id.page_title)).check(matches(withText(R.string.success_announcement)));
        onView(withId(R.id.successful_save)).check(matches(withText(R.string.successful_decision_save)));
    }

    @Test
    public void testWithMissingDecisionTextShouldWaitOnConfigurationPage() throws InterruptedException {
        // Given
        // Nothing for decision text
        onView(withId(R.id.input_option_1)).perform(typeText("Option 1"));
        onView(withId(R.id.input_option_2)).perform(typeText("Option 2")).perform(closeSoftKeyboard());
        Thread.sleep(250);

        // When
        onView(withId(R.id.button_submit_new_decision)).perform(closeSoftKeyboard()).perform(click());

        // Then
        onView(withId(R.id.page_title)).check(matches(withText(R.string.configure_decision)));
        onView(withId(R.id.input_decision_text)).check(matches(hasFocus()));
    }

    @Test
    public void testWithMissingOptionTextShouldWaitOnConfigurationPage() throws InterruptedException {
        // Given
        onView(withId(R.id.input_decision_text)).perform(typeText("Decision Text"));
        // Nothing for decision text
        onView(withId(R.id.input_option_2)).perform(typeText("Option 2")).perform(closeSoftKeyboard());
        Thread.sleep(250);

        // When
        onView(withId(R.id.button_submit_new_decision)).perform(closeSoftKeyboard()).perform(click());

        // Then
        onView(withId(R.id.page_title)).check(matches(withText(R.string.configure_decision)));
        onView(withId(R.id.input_option_1)).check(matches(hasFocus()));
    }

    @Test
    public void testWithEndDateBeforeCurrentDateShouldWaitOnConfigurationPage() throws InterruptedException {
        // Given
        onView(withId(R.id.input_decision_text)).perform(typeText("Decision Text"));

        LocalDate today = LocalDate.now();
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        today.getYear(),
                        today.getMonthValue() - 1,
                        today.getDayOfMonth()));

        onView(withId(R.id.input_option_1)).perform(typeText("Option 1"));
        onView(withId(R.id.input_option_2)).perform(typeText("Option 2")).perform(closeSoftKeyboard());
        Thread.sleep(250);

        // When
        onView(withId(R.id.button_submit_new_decision)).perform(closeSoftKeyboard()).perform(click());

        // Then
        onView(withId(R.id.page_title)).check(matches(withText(R.string.configure_decision)));
    }

}