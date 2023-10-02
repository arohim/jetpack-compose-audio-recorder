package com.him.sama.audiorecorder.presentation.feature.playerlist.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import com.him.sama.audiorecorder.presentation.designsystem.component.TopBar
import com.him.sama.audiorecorder.presentation.feature.playerlist.RecordListViewModel
import com.him.sama.audiorecorder.presentation.feature.playerlist.component.RecordItem
import com.him.sama.audiorecorder.presentation.designsystem.theme.AudioRecorderTheme
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecordListScreen(
    recordListViewModel: RecordListViewModel = koinViewModel(),
    onItemClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val records = recordListViewModel.records
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
    Content(
        records = records,
        onDelete = recordListViewModel::delete,
        onItemClick = onItemClick,
        onBack = onBack
    )
}

@Composable
private fun Content(
    records: List<AudioRecord>,
    onDelete: (AudioRecord) -> Unit,
    onItemClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "Records",
            onBackPressed = onBack
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
        ) {
            items(records.size) {
                val audioRecord = records[it]
                val date = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(Date(audioRecord.timestamp))
                val meta = "${audioRecord.duration} $date"
                RecordItem(
                    title = audioRecord.fileName,
                    meta = meta,
                    onItemClick = {
                        onItemClick(audioRecord.id)
                    },
                    onCheckedChange = {
                        onDelete(audioRecord)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecordListScreen() {
    AudioRecorderTheme {
        Content(
            records = listOf(
                AudioRecord(
                    fileName = "fileName.mp3",
                    filePath = "filePath",
                    timestamp = 1L,
                    duration = "00:10:00",
                    amsPath = null
                ),
                AudioRecord(
                    fileName = "fileName.mp3",
                    filePath = "filePath",
                    timestamp = 1L,
                    duration = "00:10:00",
                    amsPath = null
                ),
            ),
            onDelete = {},
            onItemClick = {}
        ) {}
    }
}