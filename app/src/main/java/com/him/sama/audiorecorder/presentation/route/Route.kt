package com.him.sama.audiorecorder.presentation.route

const val RECORD_SCREEN = "record_screen"
const val RECORDS_LIST_SCREEN = "records_list_screen"
const val AUDIO_PLAYER_SCREEN = "audio_player_screen/{id}"

fun getAudioPlayerScreenUri(id: Int): String {
    return AUDIO_PLAYER_SCREEN.replace("{id}", id.toString())
}