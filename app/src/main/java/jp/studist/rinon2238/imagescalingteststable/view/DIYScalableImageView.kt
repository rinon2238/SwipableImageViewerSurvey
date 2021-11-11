package jp.studist.rinon2238.imagescalingteststable.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView
import jp.studist.rinon2238.imagescalingteststable.R

class DIYScalableImageView(context: Context, attr: AttributeSet) : AppCompatImageView(context, attr) {

    private val fingersPoint = PointF()

    private val currentImageRect = RectF()
    private val currentImageFocus = PointF()

    private var scaleFactor = 1f
    private val scaleGestureListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            detector?.let {
                cutOffScaleFactorRange(detector.scaleFactor)
                imageMatrix.postScale(scaleFactor, scaleFactor)
                invalidate()
            }

            return true
        }

        private fun cutOffScaleFactorRange(currentScaleFactor: Float) {
            scaleFactor *= currentScaleFactor
            scaleFactor = 0.95f.coerceAtLeast(scaleFactor.coerceAtMost(1.05f))
        }
    }

    private val scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }
}
