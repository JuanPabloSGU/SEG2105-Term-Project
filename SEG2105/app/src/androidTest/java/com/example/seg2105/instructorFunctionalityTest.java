package com.example.seg2105;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
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

    //@Test
    public void addClass() {
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        onView(withId(R.id.cont)).perform(click());
        timeout();

        onView(withId(R.id.addClass)).perform(click());
        timeout();

        //Creating a new Class
        onView(withId(R.id.userID)).perform(typeText("testInstructor"), click(), closeSoftKeyboard());
        onView(withId(R.id.nameOfClass)).perform(typeText("testClass"), click(), closeSoftKeyboard());
        onView(withId(R.id.descriptionClass)).perform(typeText("test description"), click(), closeSoftKeyboard());
        onView(withId(R.id.day)).perform(typeText("10"), click(), closeSoftKeyboard());
        onView(withId(R.id.capacity)).perform(typeText("30"), click(), closeSoftKeyboard());

        onView(withId(R.id.createClassButton)).perform(click());

        // Go back home
        onView(Matchers.allOf(withContentDescription("Navigate up"), isDisplayed())).perform(click());
    }

    //@Test
    public void viewClasses() {
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        onView(withId(R.id.cont)).perform(click());
        timeout();

        onView(withId(R.id.viewClasses)).perform(click());
        timeout();
        timeout();
        timeout();

    }

    @Test
    public void deleteOtherClasses() throws ExecutionException, InterruptedException {

        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        onView(withId(R.id.cont)).perform(click());
        timeout();

        onView(withId(R.id.viewClasses)).perform(click());
        timeout();
        timeout();
        timeout();

        // this user will only be used in the unit tests. Thus, we can assume that all classes returned
        // by this method will be created during the test or are no longer important
        ArrayList<ClassTypes> class_types = ClassTypes.searchByInstructor("testInstructor");
        for(ClassTypes class_type : class_types){
            class_type.delete();
        }

    }

    @Test
    public void deleteOurClasses() {

    }
}
