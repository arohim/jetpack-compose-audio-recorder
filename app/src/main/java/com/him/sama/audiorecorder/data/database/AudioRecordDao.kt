package com.him.sama.audiorecorder.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.him.sama.audiorecorder.data.database.entity.AudioRecord


@Dao
interface AudioRecordDao {

    @Query("SELECT * FROM AudioRecord")
    suspend fun getAll(): List<AudioRecord>

    @Insert
    fun insert(vararg audioRecord: AudioRecord)

    @Delete
    fun delete(audioRecord: AudioRecord)

    @Delete
    fun delete(audioRecord: ArrayList<AudioRecord>)

    @Delete
    fun update(audioRecord: AudioRecord)
}