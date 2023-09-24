package com.him.sama.audiorecorder.recorder

import android.content.Context
import android.graphics.RectF
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.him.sama.audiorecorder.audioplayer.AndroidAudioPlayer
import com.him.sama.audiorecorder.recorder.Timer.OnTimerTickListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AudioRecorderViewModel constructor() : ViewModel() {

    //    private var timer: Timer? = null

    private var _isRecording = MutableStateFlow(false)
    private var _duration = MutableStateFlow("00:00.00")
    private var recorder: AndroidAudioRecorder? = null
    private var audioFile: File? = null
    private val player: AndroidAudioPlayer? = null

    private var _amplitudes = MutableStateFlow(ArrayList<Float>())
    private val _spikes = MutableStateFlow(ArrayList<RectF>())

    private var maxSpikes = 0
    private val spikeWidth = 9f
    private val spaceBetweenSpike = 6f

    private var screenWidth = 0f
    private var screenHeight = 400f

    private var timer: Timer? = null
    private val timerListener = object : OnTimerTickListener {
        override fun onTimerTick(duration: String) {
            _duration.value = duration
            recorder?.maxAmplitude()?.let { amp ->
                val normalized = (amp.toInt() / 7).coerceAtMost(400).toFloat()
                _amplitudes.value.add(normalized)
                _spikes.value.clear()

                _amplitudes.value
                    .takeLast(maxSpikes)
                    .forEachIndexed { i, ampitude ->
                        val left =
                            screenWidth - i * (spikeWidth + spaceBetweenSpike)
                        val top = screenHeight / 2 - ampitude / 2
                        val right = left + spikeWidth
                        val bottom = screenHeight / 2 + ampitude / 2
                        _spikes.value.add(RectF(left, top, right, bottom))
                    }

            }
        }
    }

    var isRecording = _isRecording.asStateFlow()
    var duration = _duration.asStateFlow()

    var spikes = _spikes.asStateFlow()
    var amplitudes = _amplitudes.asStateFlow()

    fun init(context: Context) {
        screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
        maxSpikes = (screenWidth / (spikeWidth + spaceBetweenSpike)).toInt()
    }

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
        stopRecording()
    }
}