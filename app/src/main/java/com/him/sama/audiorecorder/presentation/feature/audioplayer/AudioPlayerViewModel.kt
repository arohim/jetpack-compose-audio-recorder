package com.him.sama.audiorecorder.presentation.feature.audioplayer

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.him.sama.audiorecorder.domain.repository.AudioRecordRepository
import com.him.sama.audiorecorder.presentation.feature.audioplayer.model.AudioPlayerUi
import com.him.sama.audiorecorder.presentation.util.audioplayer.AndroidAudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.text.DecimalFormat

class AudioPlayerViewModel constructor(
    private val audioRecordRepository: AudioRecordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AudioPlayerUi())
    private var androidAudioPlayer: AndroidAudioPlayer? = null

    private var handler: Handler? = null
    private var runner: Runnable? = null
    private val delay = 1_000L

    val uiState = _uiState.asStateFlow()

    fun setAudioFile(context: Context, audioRecordId: Int) {
        viewModelScope.launch {
            val audioRecord = audioRecordRepository.get(audioRecordId)
            val file = File(audioRecord.filePath)
            androidAudioPlayer = AndroidAudioPlayer(context = context, file = file)
            val milliseconds = androidAudioPlayer?.duration?.toFloat() ?: 1f
            _uiState.value = _uiState.value.copy(
                audioRecord = audioRecord,
                maxDurationMilli = milliseconds,
                duration = audioRecord.duration
            )
            Log.d("play", "maxDuration: $milliseconds")
            handler = Handler(Looper.getMainLooper())
            runner = Runnable {
                val progressMilli = androidAudioPlayer?.currentPosition?.toFloat() ?: 0f
                val seconds = (progressMilli / 1000) % 60
                val minutes = (progressMilli / (1000 * 60) % 60)
                val hours = (progressMilli / (1000 * 60 * 60) % 24)
                Log.d("play", "progress: $progressMilli")
                _uiState.value = _uiState.value.copy(
                    progressMilli = progressMilli,
                    duration = "00:00:%s".format(DecimalFormat("00.00").format(seconds))
                )

                if (progressMilli < _uiState.value.maxDurationMilli)
                    handler?.postDelayed(runner!!, delay)
            }
        }
    }

    fun playToggle() {
        if (_uiState.value.isPlaying) {
            pause()
        } else {
            play()
        }
    }

    private fun play() {
        _uiState.value = _uiState.value.copy(isPlaying = true)
        androidAudioPlayer?.play()
        handler?.postDelayed(runner!!, delay)
    }

    private fun pause() {
        _uiState.value = _uiState.value.copy(isPlaying = false)
        androidAudioPlayer?.pause()
        handler?.removeCallbacks(runner!!)
    }

    override fun onCleared() {
        _uiState.value = _uiState.value.copy(isPlaying = false)
        handler?.removeCallbacks(runner!!)
        handler = null
        runner = null
        androidAudioPlayer?.stop()
    }
}