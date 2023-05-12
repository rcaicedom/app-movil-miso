package com.example.vinilos;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.vinilos.ui.login.LoginActivity;
import com.google.android.material.internal.NavigationMenuItemView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.view.View;


@RunWith(AndroidJUnit4.class)
public class Hu01e2e {
    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void hu01End2EndTest() throws InterruptedException {
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarVisitante),withText("Ingresar como Visitante"), isDisplayed()));
        skipBtn.perform(click());

        onView(allOf(withId(R.id.titleAlbumes))).check(matches(withText("Albumes")));
        Thread.sleep(1000);
        onView(allOf(withId(R.id.recyclerAlbumes))).check(matches(isDisplayed()));
    }

    @Test
    public void hu02End2EndTest() throws InterruptedException {
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarVisitante),withText("Ingresar como Visitante"), isDisplayed()));
        skipBtn.perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.recyclerAlbumes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(allOf((withId(R.id.albumDetalleImagen)))).check(matches(isDisplayed()));
    }

    @Test
    public void hu06End2EndTest() throws InterruptedException {
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarVisitante),withText("Ingresar como Visitante"), isDisplayed()));
        skipBtn.perform(click());
        onView(allOf(withId(R.id.artistas), isDescendantOfA(withId(R.id.albumes_bottom_navigation)))).perform(click());
        Thread.sleep(1000);
        onView(allOf(withId(R.id.artistasRecycler))).check(matches(isDisplayed()));
    }
}
