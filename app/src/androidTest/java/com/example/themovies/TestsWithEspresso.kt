package com.example.themovies

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.themovies.screens.activities.NavigationActivity
import com.example.themovies.screens.movie.data.MovieHolder
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestsWithEspresso {

    @Rule
    @JvmField
    val rule = ActivityTestRule(NavigationActivity::class.java)

    @Test
    fun testRecyclerView() {
        onView(isRoot()).perform(waitFor(2000))
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    @Test
    fun testClickOnItemRecyclerView() {
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieHolder>(
                0,
                click()
            )
        )
    }

    @Test
    fun testClickDrawerNavigation() {
        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.top_rated_movie))
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}

