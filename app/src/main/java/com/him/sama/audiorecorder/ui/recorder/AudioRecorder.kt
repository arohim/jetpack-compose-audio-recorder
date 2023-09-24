package com.him.sama.audiorecorder.ui.recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun pause()
    fun stop()
    fun maxAmplitude(): Float
}