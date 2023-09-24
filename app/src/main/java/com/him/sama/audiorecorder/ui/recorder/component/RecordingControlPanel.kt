package com.him.sama.audiorecorder.ui.recorder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.him.sama.audiorecorder.R
import com.him.sama.audiorecorder.ui.theme.AudioRecorderTheme

@Composable
fun BoxScope.ControlPanel(
    isRecording: Boolean,
    hasFileRecording: Boolean,
    onRecordingClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDoneClick: () -> Unit,
    onListClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(bottom = 72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (isRecording || hasFileRecording)
            Box(
                modifier = Modifier
                    .clickable(onClick = onDeleteClick)
                    .size(62.dp)
                    .background(Color.White.copy(alpha = 0.9f), CircleShape),
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.outline_clear_24),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        else
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .background(Color.Transparent),
            )
        Spacer(modifier = Modifier.width(16.dp))
        if (isRecording)
            Icon(
                modifier = Modifier
                    .clickable(onClick = onRecordingClick)
                    .size(84.dp),
                painter = painterResource(id = R.drawable.baseline_stop_circle_24),
                contentDescription = null,
                tint = Color.Red
            )
        else
            Box(
                modifier = Modifier
                    .clickable(onClick = onRecordingClick)
                    .size(84.dp)
                    .padding(7.dp)
                    .background(Color.Red, CircleShape)
            )
        Spacer(modifier = Modifier.width(16.dp))
        if (isRecording || hasFileRecording)
            Box(
                modifier = Modifier
                    .clickable(onClick = onDoneClick)
                    .size(62.dp)
                    .background(Color.White.copy(alpha = 0.9f), CircleShape),
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.round_done_24),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        else
            Box(
                modifier = Modifier
                    .clickable(onClick = onListClick)
                    .size(62.dp)
                    .background(Color.White.copy(alpha = 0.9f), CircleShape),
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.baseline_view_list_24),
                    contentDescription = null,
                    tint = Color.Black
                )
            }

    }
}


@Preview
@Composable
fun PreviewControlPanelRecording() {
    var isRecording by remember {
        mutableStateOf(true)
    }
    AudioRecorderTheme {
        Box {
            ControlPanel(
                isRecording = isRecording,
                hasFileRecording = false,
                onRecordingClick = {
                    isRecording = !isRecording
                },
                onDeleteClick = {},
                onDoneClick = {},
                onListClick = {}
            )
        }
    }
}

@Preview
@Composable
fun PreviewControlPanelNotRecording() {
    AudioRecorderTheme {
        Box {
            ControlPanel(
                isRecording = false,
                hasFileRecording = true,
                onRecordingClick = {},
                onDeleteClick = {},
                onDoneClick = {},
                onListClick = {})
        }
    }
}