package com.him.sama.audiorecorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.him.sama.audiorecorder.presentation.route.RECORDS_LIST_SCREEN
import com.him.sama.audiorecorder.presentation.route.RECORD_SCREEN
import com.him.sama.audiorecorder.presentation.feature.playerlist.screen.RecordListScreen
import com.him.sama.audiorecorder.presentation.feature.recorder.screen.AudioRecorderScreen
import com.him.sama.audiorecorder.presentation.designsystem.theme.AudioRecorderTheme
import com.him.sama.audiorecorder.presentation.feature.audioplayer.AudioPlayerScreen
import com.him.sama.audiorecorder.presentation.route.AUDIO_PLAYER_SCREEN
import com.him.sama.audiorecorder.presentation.route.getAudioPlayerScreenUri
import org.koin.androidx.viewmodel.ext.android.toExtras

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
                                onItemClick = { id ->
                                    navController.navigate(
                                        route = getAudioPlayerScreenUri(id),
                                        navOptions = null
                                    )
                                },
                                onBack = {
                                    navController.navigateUp()
                                }
                            )
                        }
                        composable(
                            route = AUDIO_PLAYER_SCREEN,
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) {
                            val audioRecordId: Int = it.arguments?.getInt("id", 0) ?: 0
                            AudioPlayerScreen(
                                audioRecordId = audioRecordId,
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