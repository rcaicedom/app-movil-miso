package com.example.vinilos;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.vinilos.ui.login.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class E2ETests {
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
    public void hu03End2EndTest() throws InterruptedException {
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarVisitante),withText("Ingresar como Visitante"), isDisplayed()));
        skipBtn.perform(click());
        onView(allOf(withId(R.id.artistas), isDescendantOfA(withId(R.id.albumes_bottom_navigation)))).perform(click());
        Thread.sleep(1000);
        onView(allOf(withId(R.id.artistasRecycler))).check(matches(isDisplayed()));

    }

    @Test
    public void hu04End2EndTest() throws InterruptedException{
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarVisitante),withText("Ingresar como Visitante"), isDisplayed()));
        skipBtn.perform(click());
        onView(allOf(withId(R.id.artistas), isDescendantOfA(withId(R.id.albumes_bottom_navigation)))).perform(click());
        Thread.sleep(1000);
        onView(withIndex(withId(R.id.botonArtista),0)).perform(click());
        Thread.sleep(1000);
        onView(allOf(withId(R.id.titleArtistaDetalle))).check(matches(isDisplayed()));
    }

    @Test
    public void hu05End2EndTest() throws InterruptedException {
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarVisitante),withText("Ingresar como Visitante"), isDisplayed()));
        skipBtn.perform(click());
        onView(allOf(withId(R.id.coleccionistas), isDescendantOfA(withId(R.id.albumes_bottom_navigation)))).perform(click());
        Thread.sleep(1000);
        onView(allOf(withId(R.id.coleccionistasRecycler))).check(matches(isDisplayed()));
    }
    @Test
    public void hu06End2EndTest() throws InterruptedException {
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarVisitante),withText("Ingresar como Visitante"), isDisplayed()));
        skipBtn.perform(click());
        onView(allOf(withId(R.id.coleccionistas), isDescendantOfA(withId(R.id.albumes_bottom_navigation)))).perform(click());
        Thread.sleep(1000);
        onView(withIndex(withId(R.id.botonColeccionista),0)).perform(click());
        Thread.sleep(1000);
        onView(allOf(withId(R.id.labelColeccionistaDetalleAlbumes))).check(matches(isDisplayed()));
        onView(withId(R.id.coleccionistaAlbumesRecycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);
        onView(allOf((withId(R.id.albumDetalleImagen)))).check(matches(isDisplayed()));
    }



    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
}