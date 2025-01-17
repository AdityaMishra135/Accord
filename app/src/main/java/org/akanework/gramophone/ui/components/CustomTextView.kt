package org.akanework.gramophone.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.doOnLayout

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        val FADE_COLORS_REVERSE = intArrayOf(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, 0x24FFFFFF)
    }

    var gradient: LinearGradient? = null
    val localMatrix = Matrix()
    var currentProgress = 0f
    private var firstSetProgress = true

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        doOnLayout {
            setDefaultGradient()
        }
    }

    fun setProgress(
        percent: Float,
        invalidate: Boolean = true
    ) {
        if (firstSetProgress) {
            setDefaultGradient()
            firstSetProgress = false
        }
        currentProgress = percent
        localMatrix.setTranslate(percent * width, height.toFloat())
        gradient?.setLocalMatrix(localMatrix)
        if (invalidate == true) {
            invalidate()
        }
    }

    fun setDefaultGradient() = updateGradient(FADE_COLORS_REVERSE)

    fun updateGradient(colors: IntArray) {
        gradient = LinearGradient(
            -width / 1f,
            0f,
            0f,
            0f,
            colors,
            null,
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        paint.setShader(gradient)
        super.onDraw(canvas)
        paint.setShader(null)
    }
}