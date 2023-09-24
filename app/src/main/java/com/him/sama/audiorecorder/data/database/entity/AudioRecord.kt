package com.him.sama.audiorecorder.data.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class AudioRecord(
    val fileName: String,
    val filePath: String,
    val timestamp: Long,
    val duration: String,
    val amsPath: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    var isChecked = false
}
