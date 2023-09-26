package com.him.sama.audiorecorder.presentation.util.recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun pause()
    fun stop()
    fun maxAmplitude(): Float
}