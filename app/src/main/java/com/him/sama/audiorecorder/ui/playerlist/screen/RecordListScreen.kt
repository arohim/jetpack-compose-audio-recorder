package com.him.sama.audiorecorder.ui.playerlist.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import com.him.sama.audiorecorder.ui.playerlist.RecordListViewModel
import com.him.sama.audiorecorder.ui.playerlist.component.RecordItem
import com.him.sama.audiorecorder.ui.theme.AudioRecorderTheme
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecordListScreen(
    recordListViewModel: RecordListViewModel = koinViewModel()
) {
    val records = recordListViewModel.records.collectAsStateWithLifecycle().value
    Content(records)
}

@Composable
private fun Content(records: List<AudioRecord>) {
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        items(records.size) {
            val date = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(Date(records[it].timestamp))
            val meta = "${records[it].duration} $date"
            RecordItem(title = records[it].fileName, meta = meta)
        }
    }
}

@Preview
@Composable
fun PreviewRecordListScreen() {
    AudioRecorderTheme {
        Content(listOf())
    }
}