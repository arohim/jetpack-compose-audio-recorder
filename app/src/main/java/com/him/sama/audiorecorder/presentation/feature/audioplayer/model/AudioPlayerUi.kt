package com.him.sama.audiorecorder.presentation.feature.audioplayer.model

import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import java.text.DecimalFormat

private val secondFormat = DecimalFormat("00.00")
private val minFormat = DecimalFormat("00")

data class AudioPlayerUi(
    val isPlaying: Boolean = false,
    val audioRecord: AudioRecord? = null,
    val progressMilli: Float = 0f,
    val maxDurationMilli: Float = 0f,
    val speed: Float = 1f,
) {

    val progressDuration: String
        get() {
            val seconds = (progressMilli / 1000) % 60
            val minutes = progressMilli / (1000 * 60) % 60
            val hours = progressMilli / (1000 * 60 * 60) % 24
            val secondFormatted = secondFormat.format(seconds)
            val minFormatted = minFormat.format(minutes)
            val hourFormatted = minFormat.format(hours)
            return "%s:%s:%s".format(hourFormatted, minFormatted, secondFormatted)
        }

    val duration: String
        get() {
            val seconds = (maxDurationMilli / 1000) % 60
            val minutes = maxDurationMilli / (1000 * 60) % 60
            val hours = maxDurationMilli / (1000 * 60 * 60) % 24
            val secondFormatted = secondFormat.format(seconds)
            val minFormatted = minFormat.format(minutes)
            val hourFormatted = minFormat.format(hours)
            return "%s:%s:%s".format(hourFormatted, minFormatted, secondFormatted)
        }
}