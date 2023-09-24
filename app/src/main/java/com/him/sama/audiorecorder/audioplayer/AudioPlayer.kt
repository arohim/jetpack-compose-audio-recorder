package com.him.sama.audiorecorder.audioplayer

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}