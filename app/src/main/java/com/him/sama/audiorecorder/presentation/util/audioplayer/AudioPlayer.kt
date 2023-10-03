package com.him.sama.audiorecorder.presentation.util.audioplayer

interface AudioPlayer {
    val currentPosition: Int
    val duration: Int

    fun play()
    fun stop()
    fun pause()
    fun adjustSpeed(speed: Float)
    fun seekTo(milliSecs: Int)
}