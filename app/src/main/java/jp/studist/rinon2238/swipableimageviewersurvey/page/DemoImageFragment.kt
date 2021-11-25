package jp.studist.rinon2238.swipableimageviewersurvey.page

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.github.chrisbanes.photoview.PhotoView
import jp.studist.rinon2238.swipableimageviewersurvey.DemoType
import jp.studist.rinon2238.swipableimageviewersurvey.R
import kotlin.math.floor

class DemoImageFragment: Fragment() {

    companion object {
        private const val demoTypeKey = "demo-type-key"

        fun createMe(demoType: DemoType): Fragment {
            return DemoImageFragment().apply {
                arguments = Bundle().apply {
                    putInt(demoTypeKey, demoType.resId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let { bundle ->
            val resId = bundle.getInt(demoTypeKey)
            return inflater.inflate(
                resId,
                container,
                false
            ).also {
                // 面倒なので一旦雑に落ちないようにする
                if (resId != R.layout.fragment_page_photoview) {
                    return@also
                }

                val container = it.findViewById<ConstraintLayout>(R.id.container)
                val scrollView = it.findViewById<ScrollView>(R.id.photoview_exam_description)
                it.findViewById<FrameLayout>(R.id.photoview_scroll_contents_wrapper).apply {
                    setOnClickListener {
                        switchDescriptionVisibility(scrollView, this.visibility == View.VISIBLE)
                    }
                }

                val photoview = it.findViewById<PhotoView>(R.id.photoview_exam).apply {
                    maximumScale = 6.0f
                    isZoomable = false

                    setOnScaleChangeListener { scaleFactor, _, _ ->
                        Log.d("DemoImageFragment", "scale: $scale")
                        switchDescriptionVisibility(scrollView,floorFloat(scale) > minimumScale)

                        if (scale < 1f) {
                            isZoomable = false
                        }
                    }

                    setOnClickListener {
                        switchDescriptionVisibility(scrollView,scrollView.visibility == View.VISIBLE)
                    }
                }

                val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
                    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                        container.y -= distanceY
                        return super.onScroll(e1, e2, distanceX, distanceY)
                    }

                    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                        // 速度が一定以上だったら画面を閉じる
                        if (velocityY > 8_000) {
                            dismissOrRestore(container)
                        }
                        return super.onFling(e1, e2, velocityX, velocityY)
                    }
                }

                val gestureDetector = GestureDetectorCompat(requireContext(), gestureListener)
                container.setOnTouchListener { _, event ->
                    Log.d("DemoImageFragment", "event.pointerCount = ${event.pointerCount}")
                    when (event.actionMasked) {
                        MotionEvent.ACTION_UP -> {
                            dismissOrRestore(container)
                        }

                        MotionEvent.ACTION_POINTER_DOWN -> {
                            photoview.isZoomable = true
                        }
                    }

                    gestureDetector.onTouchEvent(event)
                    true
                }
            }
        }

        return null
    }

    private fun dismissOrRestore(view: View) {
        // y方向のスワイプ量がスワイプ対象のビューの高さ1/3を超えたら画面を閉じる
        if ((view.y.toInt() != view.height) && (view.y < view.height / 3)) {
            restoreViewTransform(view)
        } else {
            // fire dismiss event.
            restoreViewTransform(view)
        }
    }

    private fun restoreViewTransform(view: View) {
        view.run {
            animate()
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .translationY(view.top.toFloat())
                .setUpdateListener {
                }
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {
                        // no op
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        // no op
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                        // no op
                    }

                    override fun onAnimationRepeat(p0: Animator?) {
                        // no op
                    }
                })
        }
    }

    private fun switchDescriptionVisibility(description: View?, shouldGoneAt: Boolean) {
        if (shouldGoneAt) {
            // TextView消す
            description?.visibility = View.GONE
        } else {
            // TextView復活
            description?.visibility = View.VISIBLE
        }
    }

    // 有効桁数 1桁のfloatに切り捨てで直す
    private fun floorFloat(input: Float): Float {
        return floor(input * 10.0f) / 10.0f
    }

}
