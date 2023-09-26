package com.him.sama.audiorecorder.domain.repository

import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import kotlinx.coroutines.flow.Flow

interface AudioRecordRepository {
    fun insert(audioRecord: AudioRecord)
    fun delete(audioRecord: AudioRecord)
    fun getAll(): Flow<List<AudioRecord>>
}