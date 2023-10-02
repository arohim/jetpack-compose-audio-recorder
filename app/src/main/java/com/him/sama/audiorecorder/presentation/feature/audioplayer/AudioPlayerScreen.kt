package com.him.sama.audiorecorder.presentation.feature.audioplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.him.sama.audiorecorder.R
import com.him.sama.audiorecorder.presentation.designsystem.component.TopBar
import com.him.sama.audiorecorder.presentation.designsystem.theme.AudioRecorderTheme
import com.him.sama.audiorecorder.presentation.designsystem.theme.BodyText
import com.him.sama.audiorecorder.presentation.designsystem.theme.Orange
import com.him.sama.audiorecorder.presentation.feature.audioplayer.model.AudioPlayerUi
import com.him.sama.audiorecorder.presentation.feature.soundwave.SoundWave
import org.koin.androidx.compose.koinViewModel

@Composable
fun AudioPlayerScreen(
    viewModel: AudioPlayerViewModel = koinViewModel(),
    audioRecordId: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.setAudioFile(context, audioRecordId)
    }
    Content(
        uiState = uiState,
        onBack = onBack,
        onPlayClick = {
            viewModel.playToggle()
        }
    )
}

@Composable
private fun Content(
    uiState: AudioPlayerUi,
    onBack: () -> Unit,
    onPlayClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = uiState.audioRecord?.fileName ?: "",
            onBackPressed = onBack
        )
        Spacer(modifier = Modifier.height(16.dp))
        SoundWave(
            modifier = Modifier.weight(1f),
            barHeight = 200.dp,
            amplitudes = List(2_000) {
                (100..800).random()
            },
            barColor = Orange,
            bgColor = Color.White,
            selectingLineColor = Color.Red
        )
        PlayerPanel(
            uiState = uiState,
            onPlayClick = onPlayClick
        )
    }
}

@Composable
fun PlayerPanel(uiState: AudioPlayerUi, onPlayClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        BodyText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = uiState.duration
        )
        Slider(
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Orange,
                activeTickColor = Orange,
                activeTrackColor = Orange
            ),
            valueRange = 0f..uiState.maxDurationMilli,
            value = uiState.progressMilli,
            onValueChange = {

            }
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            BodyText(
                modifier = Modifier
                    .background(Color.LightGray, CircleShape)
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                text = "x1",
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(16.dp))
            BodyText(
                modifier = Modifier
                    .background(Color.LightGray, CircleShape)
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                text = "x2",
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(16.dp))
            BodyText(
                modifier = Modifier
                    .background(Color.LightGray, CircleShape)
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                text = "x3",
                color = Color.DarkGray
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.ic_baseline_replay_5_24),
                contentDescription = null,
                tint = Orange
            )
            Spacer(modifier = Modifier.width(24.dp))
            Icon(
                modifier = Modifier
                    .size(72.dp)
                    .clickable(onClick = onPlayClick),
                painter = painterResource(
                    id = if (uiState.isPlaying)
                        R.drawable.ic_baseline_pause_circle_filled_24
                    else
                        R.drawable.ic_baseline_play_circle_24
                ),
                contentDescription = null,
                tint = Orange
            )
            Spacer(modifier = Modifier.width(24.dp))
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.ic_baseline_forward_5_24),
                contentDescription = null,
                tint = Orange
            )
        }
    }
}

@Preview
@Composable
fun PreviewAudioPlayerScreen() {
    AudioRecorderTheme {
        Content(
            uiState = AudioPlayerUi(),
            onBack = {},
            onPlayClick = {},
        )
    }
}