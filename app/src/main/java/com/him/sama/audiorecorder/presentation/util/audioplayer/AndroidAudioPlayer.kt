package com.him.sama.audiorecorder.presentation.util.audioplayer

import android.content.Context
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.core.net.toUri
import java.io.File

class AndroidAudioPlayer(
    context: Context,
    file: File
) : AudioPlayer {

    private var player: MediaPlayer? = null

    override val currentPosition: Int
        get() = player?.currentPosition ?: 0

    override val duration: Int
        get() = player?.duration ?: 0

    init {
        player = MediaPlayer.create(context, file.toUri())
    }

    override fun play() {
        player?.start()
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

    override fun adjustSpeed(speed: Float) {
        player?.playbackParams = PlaybackParams().setSpeed(speed)
    }

    override fun seekTo(milliSecs: Int) {
        player?.seekTo(milliSecs)
    }
}