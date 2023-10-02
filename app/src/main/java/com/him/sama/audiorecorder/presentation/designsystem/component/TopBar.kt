@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.him.sama.audiorecorder.presentation.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.him.sama.audiorecorder.R
import com.him.sama.audiorecorder.presentation.designsystem.theme.AudioRecorderTheme
import com.him.sama.audiorecorder.presentation.designsystem.theme.SubHeadThickText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit = {}
) {
    Column(modifier = modifier.height(52.dp)) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .shadow(AppBarDefaults.TopAppBarElevation)
                .zIndex(1f),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            ),
            title = {
                SubHeadThickText(title)
            },
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                        .clickable { onBackPressed() },
                    painter = painterResource(id = R.drawable.ic_baseline_chevron_left_24),
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            actions = {
            }
        )
    }
}

@Preview
@Composable
fun PreviewDafundTopBar() {
    AudioRecorderTheme {
        TopBar(
            title = "Title"
        ) {}
    }
}

