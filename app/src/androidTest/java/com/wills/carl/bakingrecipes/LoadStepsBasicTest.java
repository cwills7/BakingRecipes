package com.wills.carl.bakingrecipes;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoadStepsBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void clickRecipe_ShowSteps(){
        //1. Find the view
        //2. Perform action on the view

        onView(ViewMatchers.withId(R.id.recipe_card_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));

        //3. Check if it does what you expect


    }
}
