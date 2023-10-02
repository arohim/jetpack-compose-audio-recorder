package com.him.sama.audiorecorder.presentation.util.audioplayer

interface AudioPlayer {
    val currentPosition: Int

    fun play()
    fun stop()
    fun pause()
    val duration: Int
}