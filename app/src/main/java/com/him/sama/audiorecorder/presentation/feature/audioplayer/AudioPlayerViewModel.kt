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
import linc.com.amplituda.Amplituda
import linc.com.amplituda.exceptions.io.AmplitudaIOException
import java.io.File

class AudioPlayerViewModel constructor(
    private val audioRecordRepository: AudioRecordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AudioPlayerUi())
    private var androidAudioPlayer: AndroidAudioPlayer? = null

    private var handler: Handler? = null
    private var runner: Runnable? = null
    private val delay = 100L
    private val jumpForward = 5_000

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
            )
            getAmplitudes(context, audioRecord.filePath)
            handler = Handler(Looper.getMainLooper())
            runner = Runnable {
                val progressMilli = androidAudioPlayer?.currentPosition?.toFloat() ?: 0f
                _uiState.value = _uiState.value.copy(progressMilli = progressMilli)
                if (progressMilli < _uiState.value.maxDurationMilli) {
                    handler?.postDelayed(runner!!, delay)
                } else {
                    _uiState.value = _uiState.value.copy(isPlaying = false)
                }
            }
        }
    }

    private fun getAmplitudes(context: Context, filePath: String) {
        val amplituda = Amplituda(context)
        amplituda.processAudio(filePath)
            .get({ result ->
                val amplitudesData: List<Int> = result.amplitudesAsList()
                Log.d("amplitudes ", amplitudesData.toString())
                _uiState.value = _uiState.value.copy(amplitudes = amplitudesData)
            }) { exception ->
                if (exception is AmplitudaIOException) {
                    println("IO Exception!")
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

    fun adjustSpeed(speed: Float) {
        _uiState.value = _uiState.value.copy(speed = speed)
        androidAudioPlayer?.adjustSpeed(speed)
    }

    fun backward() {
        val currentPosition = androidAudioPlayer?.currentPosition ?: 0
        androidAudioPlayer?.seekTo(currentPosition + -jumpForward)
        val progressMilli = androidAudioPlayer?.currentPosition?.toFloat() ?: 0f
        _uiState.value = _uiState.value.copy(progressMilli = progressMilli)
    }

    fun forward() {
        val currentPosition = androidAudioPlayer?.currentPosition ?: 0
        androidAudioPlayer?.seekTo(currentPosition + jumpForward)
        val progressMilli = androidAudioPlayer?.currentPosition?.toFloat() ?: 0f
        _uiState.value = _uiState.value.copy(progressMilli = progressMilli)
    }

    fun seekTo(position: Int) {
        androidAudioPlayer?.seekTo(position)
        val progressMilli = androidAudioPlayer?.currentPosition?.toFloat() ?: 0f
        _uiState.value = _uiState.value.copy(progressMilli = progressMilli)
    }
}