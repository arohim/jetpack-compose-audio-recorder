package com.him.sama.audiorecorder.presentation.ui.recorder.model

data class AudioRecorderUiState(
    val isLoading: Boolean = false,
    val isRecording: Boolean = false,
    val duration: String = "00:00.00",
    val audioFilename: String = "",
    val amplitudes: ArrayList<Float> = arrayListOf()
)
