package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.example.ui.MainViewModel
import com.example.ui.navigation.AppNavigation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import android.app.Application

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33], instrumentedPackages = ["androidx.loader.content"])
class ComposeNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationCrash() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = MainViewModel(application)
        composeTestRule.setContent {
            com.example.ui.theme.MyApplicationTheme {
                AppNavigation(viewModel)
            }
        }
        
        composeTestRule.waitForIdle()
        
        // Wait for leads to load
        Thread.sleep(1000)
        composeTestRule.waitForIdle()

        // Wait for "HOT" to appear and click it
        composeTestRule.onAllNodesWithText("HOT")
            .onFirst()
            .performClick()
        
        composeTestRule.waitForIdle()
        
        println("Completed Compose Render!")
    }
}
