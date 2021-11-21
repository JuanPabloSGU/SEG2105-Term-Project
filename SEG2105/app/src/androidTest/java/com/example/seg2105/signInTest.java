package com.example.seg2105;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class signInTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void adminSignIn() {

        // Testing admin login information
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("admin"), click());
        onView(withId(R.id.password)).perform(typeText("admin123"), click());

        onView(withId(R.id.login)).perform(click());

    }

    @Test
    public void instructorSignIn() {

        // Testing admin login information
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("instructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click());

        onView(withId(R.id.login)).perform(click());
    }

    @Test
    public void memberSignIn() {
        // Testing admin login information
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("ramon"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click());

        onView(withId(R.id.login)).perform(click());
    }
}
