package com.him.sama.audiorecorder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.him.sama.audiorecorder.data.database.entity.AudioRecord

@Database(entities = [AudioRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun audioRecordDao(): AudioRecordDao
}