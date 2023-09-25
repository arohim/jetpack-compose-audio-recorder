package com.him.sama.audiorecorder.domain.repository

import com.him.sama.audiorecorder.data.database.entity.AudioRecord

interface AudioRecordRepository {
    fun insert(audioRecord: AudioRecord)
    fun delete(audioRecord: AudioRecord)
    suspend fun getAll(): List<AudioRecord>
}