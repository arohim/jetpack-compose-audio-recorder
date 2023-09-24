package com.him.sama.audiorecorder

import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.graphics.RectF
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.him.sama.audiorecorder.recorder.AudioRecorderViewModel
import com.him.sama.audiorecorder.recorder.ControlPanel
import com.him.sama.audiorecorder.recorder.WaveformView
import com.him.sama.audiorecorder.ui.theme.AudioRecorderTheme
import com.him.sama.audiorecorder.ui.theme.ColorText
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AudioRecorderScreen(
    viewModel: AudioRecorderViewModel = viewModel()
) {
    val context = LocalContext.current
    val isRecording = viewModel.isRecording.collectAsStateWithLifecycle().value
    val duration = viewModel.duration.collectAsStateWithLifecycle().value
    val amplitudes = viewModel.amplitudes.collectAsStateWithLifecycle().value
    val spikes = viewModel.spikes.collectAsStateWithLifecycle().value
    val coroutine = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val fileName = remember { mutableStateOf("") }

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
        viewModel.init(context = context)
    }

    Content(
        isRecording = isRecording,
        duration = duration,
        amplitudes = amplitudes,
        spikes = spikes,
        onRecordingClick = {
            viewModel.toggleRecording(context)
        },
        onDoneClick = {
            viewModel.onDone()
        },
        onDeleteClick = {
            viewModel.onDelete()
        },
        onListClick = {
            coroutine.launch {
                sheetState.show()
            }
        },
    )

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding(),
        sheetState = sheetState,
        sheetContent = {
            ConfirmToSaveFileModalContent(
                fileName,
                coroutine,
                sheetState,
                onSave = {
                    viewModel.save(fileName.value)
                }
            )
        }
    ) {
    }
}

@Composable
private fun Content(
    isRecording: Boolean,
    duration: String,
    amplitudes: ArrayList<Float>,
    spikes: ArrayList<RectF>,
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
        Display(duration, amplitudes, spikes)
        ControlPanel(
            isRecording = isRecording,
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
    amplitudes: ArrayList<Float>,
    spikes: ArrayList<RectF>
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
                it.setData(amplitudes, spikes)
            })
    }
}

@Preview
@Composable
fun PreviewAudioRecorderScreen() {
    AudioRecorderTheme {
        Content(
            isRecording = true,
            duration = "00:00.000",
            amplitudes = arrayListOf(),
            spikes = arrayListOf(),
            onRecordingClick = {},
            onDeleteClick = {},
            onDoneClick = {},
            onListClick = {}
        )
    }
}

