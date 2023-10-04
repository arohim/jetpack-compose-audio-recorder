package com.him.sama.audiorecorder.presentation.feature.playerlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.him.sama.audiorecorder.R
import com.him.sama.audiorecorder.presentation.designsystem.theme.AudioRecorderTheme
import com.him.sama.audiorecorder.presentation.designsystem.theme.BodyThickText
import com.him.sama.audiorecorder.presentation.designsystem.theme.LabelThickText

@Composable
fun RecordItem(
    title: String,
    meta: String,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onItemClick)
            .background(Color.White)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.ic_baseline_play_circle_24),
            contentDescription = null,
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .background(Color.White)
                .weight(1f),
        ) {
            BodyThickText(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            LabelThickText(
                modifier = Modifier.fillMaxWidth(),
                text = meta,
                color = Color.Gray
            )
        }
        Icon(
            modifier = Modifier
                .clickable(onClick = onDeleteClick)
                .size(24.dp)
                .minimumInteractiveComponentSize(),
            painter = painterResource(id = R.drawable.ic_baseline_delete_24),
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Preview
@Composable
fun PreviewRecordItem() {
    AudioRecorderTheme {
        RecordItem(
            title = "Hello title ".repeat(10),
            meta = "5MB, 5:24",
            onItemClick = {},
            onDeleteClick = {},
            onCheckedChange = {}
        )
    }

}