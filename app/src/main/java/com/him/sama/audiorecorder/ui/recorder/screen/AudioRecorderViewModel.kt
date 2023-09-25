package com.him.sama.audiorecorder.ui.recorder.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import com.him.sama.audiorecorder.domain.repository.AudioRecordRepository
import com.him.sama.audiorecorder.util.recorder.AndroidAudioRecorder
import com.him.sama.audiorecorder.util.timer.Timer
import com.him.sama.audiorecorder.util.timer.Timer.OnTimerTickListener
import com.him.sama.audiorecorder.ui.recorder.model.AudioRecorderUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class AudioRecorderViewModel(
    private val audioRecordRepository: AudioRecordRepository
) : ViewModel() {

    private var _showRecordSaved = MutableSharedFlow<Unit>()
    private var _showRecordDeleted = MutableSharedFlow<Unit>()
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
    var showSavedPopup = _showRecordSaved.asSharedFlow()
    var showRecordDeleted = _showRecordDeleted.asSharedFlow()

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
        viewModelScope.launch {
            _showRecordDeleted.emit(Unit)
        }
    }

    fun save(context: Context, fileName: String) {
        pauseRecording()
        val newFileName = generateFileName(context, fileName)
        val newFile = File(newFileName)
        val amplitudeFilePath = saveAmplitudes(context, fileName, _uiState.value.amplitudes)
        audioFile?.renameTo(newFile)
        viewModelScope.launch(Dispatchers.IO) {
            audioRecordRepository.insert(
                AudioRecord(
                    fileName = newFile.name,
                    filePath = newFile.path,
                    timestamp = Date().time,
                    duration = _uiState.value.duration,
                    amsPath = amplitudeFilePath
                )
            )
            stopRecording()
            _showRecordSaved.emit(Unit)
        }
    }

    private fun saveAmplitudes(
        context: Context,
        fileName: String,
        amplitudes: ArrayList<Float>
    ): String? {
        return try {
            val filePath = "${context.externalCacheDir}/$fileName"
            val file = File(filePath)
            val fos = FileOutputStream(file)
            val out = ObjectOutputStream(fos)
            out.writeObject(amplitudes)
            fos.close()
            out.close()
            filePath
        } catch (e: IOException) {
            null
        }
    }
}