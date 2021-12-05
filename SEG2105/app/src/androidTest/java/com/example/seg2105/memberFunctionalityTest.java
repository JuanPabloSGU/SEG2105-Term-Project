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

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4.class)
public class memberFunctionalityTest {

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
    public void memberSignIn(){
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testMemberShip"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());
    }

    @Test
    public void viewClasses() {
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testMemberShip"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());

        timeout();
        onView(withId(R.id.MemViewClasses)).perform(click());
    }

    @Test
    public void joinAClass(){
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testMemberShip"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());

        timeout();
        onView(withId(R.id.JoinClass)).perform(click());
    }

    @Test
    public void viewEnrolledClasses(){
        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testMemberShip"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        timeout();

        timeout();
        onView(withId(R.id.cont)).perform(click());

        timeout();
        onView(withId(R.id.ViewEnrolled)).perform(click());
    }

}
