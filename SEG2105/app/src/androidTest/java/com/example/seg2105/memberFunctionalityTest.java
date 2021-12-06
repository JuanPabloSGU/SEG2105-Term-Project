package com.example.seg2105;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import android.view.View;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

        timeout();
        timeout();
        timeout();

        // Supposed to click the join button
        clickXY(782, 28);

        timeout();
    }

    @Test
    public void joinAClassByDay(){
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

        timeout();
        onView(withId(R.id.SearchByDay)).perform(click());

        timeout();
        timeout();

        onView(withId(R.id.spinnerClass)).perform(click());
        timeout();

        timeout();
        onData(allOf(is(instanceOf(String.class)), is("Wednesday"))).perform(click());

        timeout();
        onView(withId(R.id.SearchClass)).perform(click());

        timeout();

        clickXY(782, 28);
        timeout();
    }

    @Test
    public void joinAClassByClassName(){
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

        timeout();

        onView(withId(R.id.SearchByClass)).perform(click());
        timeout();
        timeout();

        onView(withId(R.id.spinnerClass)).perform(click());
        timeout();

        timeout();
        onData(allOf(is(instanceOf(String.class)), is("Cycling"))).perform(click());

        timeout();
        onView(withId(R.id.SearchClass)).perform(click());

        timeout();

        clickXY(782, 28);

        timeout();
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

        timeout();
        timeout();
        timeout();

        clickXY(782, 28);

        timeout();

    }

    @Test
    public void unEnroll(){
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

        timeout();
        timeout();
        timeout();

        clickXY(782, 28);

        timeout();
    }

    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }

}
