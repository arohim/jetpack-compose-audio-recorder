package com.him.sama.audiorecorder.presentation.feature.playerlist.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import com.him.sama.audiorecorder.presentation.designsystem.component.TopBar
import com.him.sama.audiorecorder.presentation.feature.playerlist.RecordListViewModel
import com.him.sama.audiorecorder.presentation.feature.playerlist.component.RecordItem
import com.him.sama.audiorecorder.presentation.designsystem.theme.AudioRecorderTheme
import com.him.sama.audiorecorder.presentation.designsystem.theme.GrayDarkDisabled
import com.him.sama.audiorecorder.presentation.designsystem.theme.H5ThickText
import com.him.sama.audiorecorder.presentation.designsystem.theme.LabelText
import com.him.sama.audiorecorder.presentation.designsystem.theme.Orange
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
    var confirmToDeleteDialogTitle: AudioRecord? by remember { mutableStateOf(null) }

    val records = recordListViewModel.records
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
    Content(
        records = records,
        onDelete = {
            confirmToDeleteDialogTitle = it
        },
        onItemClick = onItemClick,
        onBack = onBack
    )

    if (confirmToDeleteDialogTitle != null) {
        Dialog(
            onDismissRequest = {
                confirmToDeleteDialogTitle = null
            }
        ) {
            DeleteConfirmation(
                confirmToDeleteDialogTitle?.fileName ?: "",
                onCancel = {
                    confirmToDeleteDialogTitle = null
                },
                onConfirm = {
                    confirmToDeleteDialogTitle?.let { it ->
                        recordListViewModel.delete(it)
                    }
                    confirmToDeleteDialogTitle = null
                }
            )
        }
    }
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
                    onDeleteClick = {
                        onDelete(audioRecord)
                    },
                    onCheckedChange = {
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

@Composable
fun DeleteConfirmation(
    title: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        H5ThickText(text = "Confirm to delete?")
        Spacer(modifier = Modifier.height(16.dp))
        H5ThickText(text = title, color = GrayDarkDisabled)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.White
                ),
                onClick = onCancel
            ) {
                LabelText(text = "Cancel", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange,
                ),
                onClick = onConfirm
            ) {
                LabelText(text = "Confirm", color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun PreviewDeleteConfirmation() {
    AudioRecorderTheme {
        DeleteConfirmation(
            title = "hello sawadeee delete.mp3",
            onCancel = {},
            onConfirm = {}
        )
    }
}