package com.example.seg2105;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class signUpTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void instructorSignUp(){

        onView(withId(R.id.SignUp)).perform(click());

        onView(withId(R.id.username)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.email)).perform(typeText("test2@gmail.com"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.instructor_button)).perform(click());

        onView(withId(R.id.signup_button)).perform(click());

        // Go back home
        onView(Matchers.allOf(withContentDescription("Navigate up"), isDisplayed())).perform(click());

        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testInstructor"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click());

        onView(withId(R.id.login)).perform(click());
    }

    @Test
    public void memberSignUp(){

        onView(withId(R.id.SignUp)).perform(click());

        onView(withId(R.id.username)).perform(typeText("testMember"), click());
        onView(withId(R.id.email)).perform(typeText("test3@gmail.com"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click(), closeSoftKeyboard());

        onView(withId(R.id.gymMember_button)).perform(click());

        onView(withId(R.id.signup_button)).perform(click());

        // Go back home
        onView(Matchers.allOf(withContentDescription("Navigate up"), isDisplayed())).perform(click());

        // Now seeing if our account was created
        onView(withId(R.id.SignIn)).perform(click());

        // Input information
        onView(withId(R.id.user_name)).perform(typeText("testMember"), click());
        onView(withId(R.id.password)).perform(typeText("password1234"), click());

        onView(withId(R.id.login)).perform(click());
    }
}
