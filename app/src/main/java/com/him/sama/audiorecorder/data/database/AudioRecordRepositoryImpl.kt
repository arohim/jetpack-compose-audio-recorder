package com.him.sama.audiorecorder.data.database

import com.him.sama.audiorecorder.data.database.entity.AudioRecord
import com.him.sama.audiorecorder.domain.repository.AudioRecordRepository
import kotlinx.coroutines.flow.Flow

class AudioRecordRepositoryImpl constructor(
    private val dao: AudioRecordDao
) : AudioRecordRepository {

    override fun insert(audioRecord: AudioRecord) {
        dao.insert(audioRecord)
    }

    override fun delete(audioRecord: AudioRecord) {
        dao.delete(audioRecord)
    }

    override fun getAll(): Flow<List<AudioRecord>> {
        return dao.getAll()
    }
}