package com.example.pruebaa.ui.Users

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.pruebaa.R
import com.example.pruebaa.ui.Adapter.UsersAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityScene: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    private var decorView: View?=null

    @Before
    fun checkActivityVisibility(){
        activityScene.scenario.onActivity {
            decorView=it.window.decorView
        }
    }

    @After
    fun tearDown(){
        activityScene.scenario.close()
    }

    @org.junit.Test
    fun ifUserSearch(){
        Espresso.onView(withId(R.id.svUsers))
            .perform(ViewActions.click())
    }



    @org.junit.Test
    fun clickItem() {
        Espresso.onView(withId(R.id.rvUsers))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<UsersAdapter.ViewHolder>(0, ViewActions.click()))

    }


}