package com.him.sama.audiorecorder.presentation.ui.recorder.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class WaveformView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint: Paint = Paint()
    private var spikes = ArrayList<RectF>()

    private var radius = 6f

    private var maxSpikes = 0
    private val spikeWidth = 9f
    private val spaceBetweenSpike = 6f

    private var screenWidth = 0f
    private var screenHeight = 400f

    init {
        paint.color = Color.rgb(244, 81, 30)
        screenWidth = resources.displayMetrics.widthPixels.toFloat()
        maxSpikes = (screenWidth / (spikeWidth + spaceBetweenSpike)).toInt()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        spikes.forEach {
            canvas.drawRoundRect(it, radius, radius, paint)
        }
    }

    fun setData(amplitudes: ArrayList<Float>) {
        this.spikes.clear()
        amplitudes
            .takeLast(maxSpikes)
            .forEachIndexed { i, amplitude ->
                val left =
                    screenWidth - i * (spikeWidth + spaceBetweenSpike)
                val top = screenHeight / 2 - amplitude / 2
                val right = left + spikeWidth
                val bottom = screenHeight / 2 + amplitude / 2
                spikes.add(RectF(left, top, right, bottom))
            }
        invalidate()
    }
}