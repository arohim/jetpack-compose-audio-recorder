package com.him.sama.audiorecorder.recorder

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.him.sama.audiorecorder.audioplayer.AndroidAudioPlayer
import com.him.sama.audiorecorder.recorder.Timer.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter

class AudioRecorderViewModel constructor(

) : ViewModel() {

    //    private var timer: Timer? = null

    private var _isRecording = MutableStateFlow(false)
    private var _duration = MutableStateFlow("00:00.00")
    private var recorder: AndroidAudioRecorder? = null
    private var audioFile: File? = null
    private val player: AndroidAudioPlayer? = null

    private var timer: Timer? = null
    private val timerListener = object : OnTimerTickListener {
        override fun onTimerTick(duration: String) {
            _duration.value = duration
        }
    }

    var isRecording = _isRecording.asStateFlow()
    var duration = _duration.asStateFlow()

    fun toggleRecording(context: Context) {
        if (_isRecording.value) {
            pauseRecording()
        } else {
            startRecording(context)
        }
    }

    private fun startRecording(context: Context) {
        _isRecording.value = true
        if (recorder == null || audioFile == null) {
            recorder = AndroidAudioRecorder(context)
            val date = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault())
                .format(Date())
            audioFile = File("${context.externalCacheDir}/audio_file_$date.mp3")
        }

        audioFile?.let {
            recorder?.start(it)
        }
        if (timer == null)
            timer = Timer(listener = timerListener)

        timer?.start()
    }

    private fun stopRecording() {
        _isRecording.value = false
        recorder?.stop()
        recorder = null
        timer?.stop()
        timer = null
        audioFile = null
    }

    private fun pauseRecording() {
        _isRecording.value = false
        recorder?.pause()
        timer?.pause()
    }

    override fun onCleared() {
        super.onCleared()
        timer = null
    }
}