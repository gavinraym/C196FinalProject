package com.example.finalproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.screenshot.Screenshot;
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor;
import androidx.test.runner.screenshot.ScreenCapture;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;


//https://developer.android.com/reference/androidx/test/espresso/action/ViewActions
public class MainActivityTest {

    private final String pathname = "Main test";


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule(MainActivity.class);

    @Test
    public void createNewTerms() throws IOException, InterruptedException {
        DataFrame df = new DataFrame();
        df.setTermTitle("Test Term");
        onView(withId(R.id.editTextTermTitle)).perform(replaceText("test term"));
        onView(withId(R.id.editTextStartDate)).perform(replaceText("2022/10/2"));
        onView(withId(R.id.editTextEndDate)).perform(replaceText("2022/10/2"));
        SauceLabsCustomScreenshot.capture(pathname);
        onView(withId(R.id.createTermButton)).perform(click());
        Thread.sleep(20000);




        SauceLabsCustomScreenshot.capture(pathname);
    }



}