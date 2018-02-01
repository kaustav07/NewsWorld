package com.android.chatterjeekaustav.newsworld.ui.splash;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.android.chatterjeekaustav.newsworld.R;
import com.madcoders.chatterjeekaustav.newsworld.ui.splash.SplashActivity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NewsListActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Before
    public void doit() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void initialScreenItemsCheck() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withContentDescription("Open")).check(matches(isDisplayed()));
        onView(withId(R.id.app_bar_search)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(withId(R.id.newslist)).check(matches(isDisplayed()));
    }

    @Test
    public void toolbarTest() {

        ViewInteraction toolbar = onView(withContentDescription("Open"));
        toolbar.check(matches(isDisplayed()));
        toolbar.perform(click());

        //Checking main Items
        onView(withId(R.id.material_drawer_account_header)).check(matches(isDisplayed()));
        onView(withText(R.string.homeItem)).check(matches(isDisplayed()));
        onView(withText(R.string.categoryMainItem)).check(matches(isDisplayed()));
        onView(withText(R.string.clearCache)).check(matches(isDisplayed()));
        onView(withText(R.string.share)).check(matches(isDisplayed()));
        onView(withText(R.string.feedback)).check(matches(isDisplayed()));
        onView(withText(R.string.exit)).check(matches(isDisplayed()));
        onView(withText(R.string.footertext)).check(matches(isDisplayed()));

        //Checking All categories
        onView(withText(R.string.categoryMainItem)).perform(click());
        onView(withText(R.string.categoryMainItem)).perform(swipeUp());
        onView(withText(R.string.categoryMainItem)).perform(swipeUp());
        onView(withText(R.string.categoryMainItem)).perform(swipeUp());
        onView(withText(R.string.categoryMainItem)).perform(swipeUp());
        onView(withText(R.string.categoryMainItem)).perform(swipeUp());
        onView(withText(R.string.categoryMainItem)).perform(swipeUp());
        onView(withText(R.string.categoryMainItem)).perform(swipeUp());
        onView(withText(R.string.categoryMainItem)).perform(swipeUp());

        onView(withText(R.string.categoryBusiness)).check(matches(isDisplayed()));
        onView(withText(R.string.categoryEntertainment)).check(matches(isDisplayed()));
        onView(withText(R.string.categoryGeneral)).check(matches(isDisplayed()));
        onView(withText(R.string.categoryHealth)).check(matches(isDisplayed()));
        onView(withText(R.string.categoryScience)).check(matches(isDisplayed()));
        onView(withText(R.string.categorySports)).check(matches(isDisplayed()));
        onView(withText(R.string.categoryTechnology)).check(matches(isDisplayed()));
        onView(withText(R.string.categoryMainItem)).perform(click());

    }
}
