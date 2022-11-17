package com.example.applicationcuisiner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void checkFoodie() {
        onView(withText("FOODIE")).check(matches(isDisplayed()));

    }
    @Test
    public void isConnectionClickable() {
        onView(withText("CONNEXION")).check(matches(isClickable()));
    }
    @Test
    public void isRegistrationClientClickable() {
        onView(withText("S'INSCRIRE EN TANT QUE CLIENT")).check(matches(isClickable()));
    }
    @Test
    public void isRegistrationCookClickable() {
        onView(withText("S'INSCRIRE EN TANT QUE CUISINIER")).check(matches(isClickable()));
    }




}