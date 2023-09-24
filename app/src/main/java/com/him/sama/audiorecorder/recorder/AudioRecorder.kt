package com.him.sama.audiorecorder.recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun pause()
    fun stop()
}