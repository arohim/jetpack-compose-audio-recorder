package com.him.sama.audiorecorder.presentation.util.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AndroidAudioRecorder(
    private val context: Context
) : AudioRecorder {

    var dirPath = ""
    var fileName = ""
    private var recorder: MediaRecorder? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
    }

    override fun start(outputFile: File) {
        dirPath = context.externalCacheDir?.absolutePath + "/"
        val simpleDateFormat = SimpleDateFormat("yyy_MM_DD_hh_mm_ss", Locale.getDefault())
        val date = simpleDateFormat.format(Date())
        fileName = "audio_record_$date"

        recorder = createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            try {
                prepare()
            } catch (_: IOException) {
            }
            start()
        }
    }

    override fun pause() {
        recorder?.pause()
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }

    override fun maxAmplitude(): Float {
        return recorder?.maxAmplitude?.toFloat() ?: 0f
    }
}