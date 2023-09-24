package com.him.sama.audiorecorder.ui.audioplayer

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}