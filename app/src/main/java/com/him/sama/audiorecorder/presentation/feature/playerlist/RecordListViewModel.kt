package com.him.sama.audiorecorder.presentation.feature.playerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import com.him.sama.audiorecorder.domain.repository.AudioRecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordListViewModel(
    private val repository: AudioRecordRepository
) : ViewModel() {
    val records = repository.getAll()

    fun delete(audioRecord: AudioRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(audioRecord)
        }
    }

}