package com.him.sama.audiorecorder.ui.playerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import com.him.sama.audiorecorder.domain.repository.AudioRecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecordListViewModel(
    private val repository: AudioRecordRepository
) : ViewModel() {

    private val _records = MutableStateFlow<List<AudioRecord>>(listOf())

    val records = _records.asStateFlow()

    init {
        viewModelScope.launch {
            _records.value = repository.getAll()
        }
    }

}