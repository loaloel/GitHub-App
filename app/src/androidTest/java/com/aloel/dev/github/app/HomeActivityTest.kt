package com.aloel.dev.github.app

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aloel.dev.github.features.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Alul on 28/04/2025.
 * Senior Android Developer
 */

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun search_withResults_showsList() {
        onView(withId(R.id.searchView))
            .perform(click())

        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("Fakhruddin"), closeSoftKeyboard())

        Thread.sleep(2000)

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun search_withNoResults_showsNotFoundMessage() {
        onView(withId(R.id.searchView))
            .perform(click())

        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("xyzxyznotfound"), closeSoftKeyboard())

        Thread.sleep(2000)

        onView(withText("User not found"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun emptyQuery_showsLetsSearchMessage() {
        onView(withId(R.id.searchView))
            .perform(click())

        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("Fakhruddin"), closeSoftKeyboard())

        Thread.sleep(2000)

        onView(isAssignableFrom(EditText::class.java))
            .perform(clearText())

        Thread.sleep(2000)

        onView(withText("Let's search"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun open_repository_with_custom_tab() {
        onView(withId(R.id.searchView))
            .perform(click())

        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("Fakhruddin"), closeSoftKeyboard())

        Thread.sleep(2000)

        onView(withId(R.id.recyclerView)) // Find the RecyclerView
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(2000)

        onView(withId(R.id.recyclerView)) // Find the RecyclerView
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }
}

