package com.him.sama.audiorecorder.presentation.util.audioplayer

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}