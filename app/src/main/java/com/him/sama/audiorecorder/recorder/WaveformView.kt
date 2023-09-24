package com.him.sama.audiorecorder.recorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class WaveformView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint: Paint = Paint()
    private var amplitudes = ArrayList<Float>()
    private var spikes = ArrayList<RectF>()

    private var radius = 6f
    private var w = 9f

    private var sw = 0f
    private var sh = 400f

    init {
        paint.color = Color.rgb(244, 81, 30)

        sw = resources.displayMetrics.widthPixels.toFloat()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        spikes.forEach {
            canvas?.drawRoundRect(it, radius, radius, paint)
        }
    }

    fun addAmplitude(amp: Float) {
        amplitudes.add(amp)

        val left = 0f
        val top = 0f
        val right = left + w
        val bottom = amp

        spikes.add(RectF(left, top, right, bottom))
        invalidate()
    }

    fun setData(amplitudes: ArrayList<Float>, spikes: ArrayList<RectF>) {
        this.amplitudes = amplitudes
        this.spikes = spikes
        invalidate()
    }
}