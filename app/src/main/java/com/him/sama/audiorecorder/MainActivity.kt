package com.him.sama.audiorecorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.him.sama.audiorecorder.presentation.route.RECORDS_LIST_SCREEN
import com.him.sama.audiorecorder.presentation.route.RECORD_SCREEN
import com.him.sama.audiorecorder.presentation.feature.playerlist.screen.RecordListScreen
import com.him.sama.audiorecorder.presentation.feature.recorder.screen.AudioRecorderScreen
import com.him.sama.audiorecorder.presentation.designsystem.theme.AudioRecorderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioRecorderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = RECORD_SCREEN
                    ) {
                        composable(RECORD_SCREEN) {
                            AudioRecorderScreen(
                                onNavigateToList = {
                                    navController.navigate(RECORDS_LIST_SCREEN)
                                }
                            )
                        }
                        composable(RECORDS_LIST_SCREEN) {
                            RecordListScreen(
                                onBack = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}