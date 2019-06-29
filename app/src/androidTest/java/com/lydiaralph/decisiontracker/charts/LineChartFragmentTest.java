//package com.lydiaralph.decisiontracker.charts;
//
//import android.content.Intent;
//
//import com.github.mikephil.charting.data.LineData;
//import com.lydiaralph.decisiontracker.R;
//import com.lydiaralph.decisiontracker.activities.ViewDecisionDetailActivity;
//import com.lydiaralph.decisiontracker.activities.ViewDecisionsCategoryActivity;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.rule.ActivityTestRule;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//
//@RunWith(AndroidJUnit4.class)
//public class LineChartFragmentTest {
//
//    private LineChartFragment underTest;
//
//    @Rule
//    public ActivityTestRule<LineChartFragment> activityTestRule =
//            new ActivityTestRule<LineChartFragment>(LineChartFragment.class){
//
//                @Override
//                protected Intent getActivityIntent() {
//                    Intent intent = new Intent();
//                    // TODO: Same problem as before: this data is not in database.
//                    // Wire in test database? or mock database? Mock ViewModel?
//                    intent.putExtra(ViewDecisionsCategoryActivity.VIEW_DECISION_ID, 1);
//                    return intent;
//                }
//            };
//
//
//    @Before
//    public void init(){
//        underTest = new LineChartFragment();
//        underTest.displayData(SampleChartData.getSampleChartData());
//    }
//
//    @Test
//    public void testLineChartIsDisplayed() {
//        activityTestRule.getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragment_container, underTest)
//                .commit();
//
//        // Need to add fragment to fragment manager??
//
//        onView(withId(R.id.spinner1)).perform(click());
//        onView(withId(R.id.line_chart)).check(matches((isDisplayed())));
//        onView(withId(R.id.text)).check(matches(withText("Hello World!")));
//
////        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("This Test Works")))
////                .check(matches(isDisplayed()));
//
////        LineData returnedDataSet = LineChartFragment.setData(inputData);
////
////        assertThat(returnedDataSet.getDataSetCount(), equalTo(3));
////
////        assertThat(returnedDataSet.getDataSetByIndex(0).getLabel(), equalTo("Abdss"));
////        assertThat(returnedDataSet.getDataSetByIndex(1).getLabel(), equalTo("geowig"));
////        assertThat(returnedDataSet.getDataSetByIndex(2).getLabel(), equalTo("Zgew"));
////
////        ILineDataSet firstDataSet = returnedDataSet.getDataSetByIndex(0);
////        assertThat("Null mood intensity should be ignored",
////                firstDataSet.getEntryCount(), equalTo(3));
////
////        assertThat(firstDataSet.getEntryForIndex(0).getX(), equalTo(18055f));
////        assertThat(firstDataSet.getEntryForIndex(0).getY(), equalTo(74f));
////
////        assertThat(firstDataSet.getEntryForIndex(1).getY(), equalTo(85f));
////        assertThat(firstDataSet.getEntryForIndex(2).getY(), equalTo(23f));
//    }
//}
