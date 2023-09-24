package com.him.sama.audiorecorder.recorder

import android.content.Context
import androidx.lifecycle.ViewModel
import com.him.sama.audiorecorder.recorder.Timer.OnTimerTickListener
import com.him.sama.audiorecorder.recorder.model.AudioRecorderUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AudioRecorderViewModel : ViewModel() {

    private var _showSavedPopup = MutableSharedFlow<Unit>()
    private var _uiState = MutableStateFlow(AudioRecorderUiState())
    private var recorder: AndroidAudioRecorder? = null
    private var audioFile: File? = null

    private var timer: Timer? = null
    private val timerListener = object : OnTimerTickListener {
        override fun onTimerTick(duration: String) {
            recorder?.maxAmplitude()?.let { amp ->
                val normalized = (amp.toInt() / 7).coerceAtMost(400).toFloat()
                _uiState.value = _uiState.value.copy(
                    duration = duration,
                    amplitudes = _uiState.value.amplitudes.also {
                        it.add(normalized)
                    }
                )
            }
        }
    }


    var uiState = _uiState.asStateFlow()
    var showSavedPopup = _showSavedPopup.asSharedFlow()

    fun toggleRecording(context: Context) {
        if (_uiState.value.isRecording) {
            pauseRecording()
        } else {
            startRecording(context)
        }
    }

    private fun startRecording(context: Context) {
        if (recorder == null || audioFile == null) {
            recorder = AndroidAudioRecorder(context)
            val fileName = generateFileName(context)
            audioFile = File(fileName)
        }

        audioFile?.let {
            recorder?.start(it)
        }
        if (timer == null)
            timer = Timer(listener = timerListener)

        timer?.start()
        _uiState.value = _uiState.value.copy(
            isRecording = true,
            audioFilename = audioFile?.name ?: ""
        )
    }

    private fun generateFileName(context: Context, fileName: String? = null): String {
        return if (fileName?.isNotEmpty() == true) {
            "${context.externalCacheDir}/$fileName.mp3"
        } else {
            val date = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault()).format(Date())
            "${context.externalCacheDir}/audio_file_$date.mp3"
        }
    }

    private fun stopRecording() {
        _uiState.value = AudioRecorderUiState()
        recorder?.stop()
        recorder = null
        timer?.stop()
        timer = null
        audioFile = null
    }

    fun pauseRecording() {
        _uiState.value = _uiState.value.copy(isRecording = false)
        recorder?.pause()
        timer?.pause()
    }

    override fun onCleared() {
        super.onCleared()
        stopRecording()
    }

    fun onDelete() {
        audioFile?.delete()
        stopRecording()
    }

    fun save(context: Context, fileName: String) {
        pauseRecording()
        val newFileName = generateFileName(context, fileName)
        audioFile?.renameTo(File(newFileName))
        stopRecording()
    }
}