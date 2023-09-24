package com.him.sama.audiorecorder.ui.audioplayer

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AndroidAudioPlayer(private val context: Context) : AudioPlayer {

    private var player: MediaPlayer? = null


    override fun playFile(file: File) {
        player = MediaPlayer.create(context, file.toUri()).apply {
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}