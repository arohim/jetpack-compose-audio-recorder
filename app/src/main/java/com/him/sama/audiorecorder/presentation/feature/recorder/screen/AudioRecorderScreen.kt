package com.him.sama.audiorecorder.presentation.feature.recorder.screen

import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.him.sama.audiorecorder.presentation.feature.recorder.component.WaveformView
import com.him.sama.audiorecorder.presentation.feature.recorder.component.ConfirmToSaveFileModalContent
import com.him.sama.audiorecorder.presentation.feature.recorder.component.ControlPanel
import com.him.sama.audiorecorder.presentation.designsystem.theme.AudioRecorderTheme
import com.him.sama.audiorecorder.presentation.designsystem.theme.ColorText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AudioRecorderScreen(
    viewModel: AudioRecorderViewModel = koinViewModel(),
    onNavigateToList: () -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val coroutine = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val keyboardController = LocalSoftwareKeyboardController.current

    var permissionGranted by remember {
        mutableStateOf(
            ActivityCompat.checkSelfPermission(
                context,
                RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
    }
    if (!permissionGranted) {
        LaunchedEffect(Unit) {
            launcher.launch(RECORD_AUDIO)
        }
    }

    LaunchedEffect(Unit) {
        launch {
            viewModel.showSavedPopup.collectLatest {
                Toast.makeText(context, "Record saved", Toast.LENGTH_LONG).show()
            }
        }
        launch {
            viewModel.showRecordDeleted.collectLatest {
                Toast.makeText(context, "Record deleted", Toast.LENGTH_LONG).show()
            }
        }
    }

    Content(
        isRecording = uiState.isRecording,
        hasFileRecording = uiState.audioFilename.isNotEmpty(),
        duration = uiState.duration,
        amplitudes = uiState.amplitudes,
        onRecordingClick = {
            viewModel.toggleRecording(context)
        },
        onDeleteClick = {
            viewModel.onDelete()
        },
        onDoneClick = {
            viewModel.pauseRecording()
            coroutine.launch {
                sheetState.show()
            }
        },
        onListClick = onNavigateToList
    )

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding(),
        sheetState = sheetState,
        sheetContent = {
            ConfirmToSaveFileModalContent(
                coroutine = coroutine,
                keyboardController = keyboardController,
                defaultFileName = uiState.audioFilename,
                sheetState = sheetState
            ) {
                coroutine.launch {
                    sheetState.hide()
                    keyboardController?.hide()
                    viewModel.save(context, it)
                }
            }
        }
    ) {
    }
}

@Composable
private fun Content(
    isRecording: Boolean,
    hasFileRecording: Boolean,
    duration: String,
    amplitudes: ArrayList<Float>,
    onRecordingClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDoneClick: () -> Unit,
    onListClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Display(duration, amplitudes)
        ControlPanel(
            isRecording = isRecording,
            hasFileRecording = hasFileRecording,
            onRecordingClick = onRecordingClick,
            onDeleteClick = onDeleteClick,
            onDoneClick = onDoneClick,
            onListClick = onListClick,
        )
    }
}

@Composable
private fun BoxScope.Display(
    duration: String,
    amplitudes: ArrayList<Float>
) {
    Column(
        modifier = Modifier.Companion
            .align(Alignment.Center)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = duration,
            style = MaterialTheme.typography.displayLarge
                .copy(color = ColorText)
        )
        Spacer(modifier = Modifier.height(24.dp))
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clipToBounds(),
            factory = {
                WaveformView(context = it, attrs = null)
            }, update = {
                it.setData(amplitudes)
            })
    }
}

@Preview
@Composable
fun PreviewAudioRecorderScreen() {
    AudioRecorderTheme {
        Content(
            isRecording = true,
            hasFileRecording = true,
            duration = "00:00.000",
            amplitudes = arrayListOf(),
            onRecordingClick = {},
            onDeleteClick = {},
            onDoneClick = {}
        ) {}
    }
}

