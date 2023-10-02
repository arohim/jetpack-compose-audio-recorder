package com.him.sama.audiorecorder.presentation.feature.audioplayer.model

import com.him.sama.audiorecorder.data.database.entity.AudioRecord

data class AudioPlayerUi(
    val isPlaying: Boolean = false,
    val audioRecord: AudioRecord? = null,
    val progressMilli: Float = 0f,
    val maxDurationMilli: Float = 0f,
    val duration: String = "00:00:00.00",
)