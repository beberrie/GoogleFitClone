package ph.edu.usc24104013.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    var heartProgress: Float = 0f
        set(value) { field = value.coerceIn(0f, 1f); invalidate() }

    var stepsProgress: Float = 0f
        set(value) { field = value.coerceIn(0f, 1f); invalidate() }

    // Outer track — accent_lowgreen
    private val outerTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = 0xFF1C302E.toInt()  // accent_lowgreen
    }

    // Outer value — accent_green
    private val outerValuePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style     = Paint.Style.STROKE
        color     = 0xFF06DAAE.toInt()  // accent_green
        strokeCap = Paint.Cap.ROUND
    }

    // Inner track — accent_lowblue
    private val innerTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = 0xFF1D2B3B.toInt()  // accent_lowblue
    }

    // Inner value — accent_blue
    private val innerValuePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style     = Paint.Style.STROKE
        color     = 0xFF6E9AE3.toInt()  // accent_blue
        strokeCap = Paint.Cap.ROUND
    }

    private val outerRect = RectF()
    private val innerRect = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx     = width / 2f
        val cy     = height / 2f
        val stroke = width * 0.045f   // thin stroke
        val gap    = stroke * 2.2f    // gap between rings

        outerTrackPaint.strokeWidth = stroke
        outerValuePaint.strokeWidth = stroke
        innerTrackPaint.strokeWidth = stroke
        innerValuePaint.strokeWidth = stroke

        // Outer ring (Heart Points)
        val outerR = cx - stroke
        outerRect.set(cx - outerR, cy - outerR, cx + outerR, cy + outerR)
        canvas.drawOval(outerRect, outerTrackPaint)
        canvas.drawArc(outerRect, -90f, 360f * heartProgress, false, outerValuePaint)

        // Inner ring (Steps)
        val innerR = outerR - gap - stroke
        innerRect.set(cx - innerR, cy - innerR, cx + innerR, cy + innerR)
        canvas.drawOval(innerRect, innerTrackPaint)
        canvas.drawArc(innerRect, -90f, 360f * stepsProgress, false, innerValuePaint)
    }
}