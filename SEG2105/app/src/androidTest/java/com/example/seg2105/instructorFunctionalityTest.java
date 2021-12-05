package com.example.seg2105;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

import androidx.recyclerview.widget.RecyclerView;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4.class)
public class instructorFunctionalityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    private void timeout() {
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void teachClass() {
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());

        onView(withId(R.id.create_scheduled_class)).perform(click());
        timeout();

        onView(withId(R.id.spinner3)).perform(click());
        timeout();

        onData(allOf(is(instanceOf(String.class)), is("Cycling"))).perform(click());
        // Clicked the Tennis class

        timeout();

        // Select the day to teach
        timeout();
        onView(withId(R.id.spinner2)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Friday"))).perform(click());

        // Select the difficulty
        timeout();
        onView(withId(R.id.spinner4)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Intermediate"))).perform(click());

        timeout();
        onView(withId(R.id.spinner5)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("14:00-15:00"))).perform(click());

        timeout();
        // Enter capacity of the class
        onView(withId(R.id.capacity)).perform(typeText(String.valueOf("30")), closeSoftKeyboard());

        onView(withId(R.id.createScheduledClassButton)).perform(click());
    }

    @Test
    public void searchClassByInstructor() {
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());

        timeout();
        onView(withId(R.id.SearchByInstr)).perform(click());

        timeout();
        timeout();
        timeout();
        //select Instructor
        onView(withId(R.id.spinnerInstr)).perform(click());

        timeout();
        onData(allOf(is(instanceOf(String.class)), is("instructor"))).perform(click());

        timeout();

        onView(withId(R.id.SearchInstr)).perform(click());
    }

    @Test
    public void searchByClassName() {
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());

        timeout();
        onView(withId(R.id.SearchByClass)).perform(click());


        timeout();
        onView(withId(R.id.spinnerClass)).perform(click());

        timeout();
        onData(allOf(is(instanceOf(String.class)), is("Yoga"))).perform(click());

        timeout();
        onView(withId(R.id.SearchClass)).perform(click());

        timeout();
    }

    @Test
    public void searchScheduleClasses() {
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());

        timeout();
        onView(withId(R.id.SearchAll)).perform(click());
    }

    @Test
    public void deleteClass(){
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());

        timeout();
        onView(withId(R.id.SearchAll)).perform(click());

        timeout();
        timeout();

        onData(withId(R.id.search_recycler_view))
                .atPosition(0).onChildView(withText("DELETE"))
                .perform(click());

    }
}
