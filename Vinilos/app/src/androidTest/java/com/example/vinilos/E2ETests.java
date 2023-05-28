package com.example.vinilos;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.anyOf;

import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.vinilos.ui.login.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;


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

    @Test
    public void hu7End2EndTest() throws InterruptedException {
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarColeccionista),withText("Ingresar como Coleccionista"), isDisplayed()));
        skipBtn.perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.agregarAlbumButton)).perform(click());
        Thread.sleep(1000);
        onView(allOf(withId(R.id.titleCrear))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.editCrearNombre))).perform(typeText("album test"));
        onView(allOf(withId(R.id.editCrearCover))).perform(typeText("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fphoto%2Fnewborn-chick-gm466167557-33570558&psig=AOvVaw2BQuuQrKz9DRSysMsDNEUN&ust=1685298117202000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCODViu-Olv8CFQAAAAAdAAAAABAD"));
        onView(allOf(withId(R.id.editCrearFecha))).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 06, 27));
        Thread.sleep(3000);
        onView(anyOf(withText(android.R.string.ok))).inRoot(isDialog()).perform(click());
        onView(allOf(withId(R.id.editCrearDescripcion))).perform(typeText("test description")).perform(closeSoftKeyboard());
        onView(allOf(withId(R.id.spinnerCrearGenero))).perform(click());
        onView(allOf(withText("Salsa"))).perform(click());
        onView(allOf(withId(R.id.spinnerCrearCasa))).perform(click());
        onView(allOf(withText("EMI"))).perform(click());
        Thread.sleep(1000);
        onView(allOf(withId(R.id.buttonCrearAlbum))).perform(click());
    }

    @Test
    public void hu8End2EndTest() throws InterruptedException {
        ViewInteraction skipBtn = onView(allOf(withId(R.id.ingresarColeccionista),withText("Ingresar como Coleccionista"), isDisplayed()));
        skipBtn.perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.recyclerAlbumes)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        Thread.sleep(1000);
        onView(withId(R.id.buttonAsociarTrack)).perform(click());
        onView(allOf(withId(R.id.titleAsociar))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.editAsociarNombre))).perform(typeText(" test track"));
        onView(allOf(withId(R.id.editAsociarDuracion))).perform(typeText("500")).perform(closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAsociar))).perform(click());
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