package com.wills.carl.bakingrecipes;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.CardView;

import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class LoadStepsBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void startupShowsRecipes(){
        //1. Find the view
        //2. Perform action on the view

        onView(withId(R.id.recipe_card_rv)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()))
                .check(matches(hasDescendant(withText("Nutella Pie"))));

        //3. Check if it does what you expect
    }

    @Test
    public void recipeClickShowsIntroStep(){
        onView(withId(R.id.recipe_card_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText("0. Recipe Introduction")).check(matches(isDisplayed()));
    }

    @Test
    public void recipeStepClickShowsStepInfo(){
        onView(withId(R.id.recipe_card_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onData(anything()).inAdapterView(withId(R.id.recipe_detail_gv)).atPosition(0).perform(click());
        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
    }
}
