package jp.studist.rinon2238.swipableimageviewersurvey

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.viewpager2.widget.ViewPager2
import jp.studist.rinon2238.swipableimageviewersurvey.pager.DemoFragmentStateAdapter

class PhotoViewDemoActivity: AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var gestureDetector: GestureDetectorCompat

    companion object {
        private const val ORIENTATION_KEY = "orientation_key"

        fun createIntent(context: Context, doVerticalization: Boolean): Intent {
            return Intent(context, PhotoViewDemoActivity::class.java).apply {
                putExtra(ORIENTATION_KEY, doVerticalization)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_demo_container)

        viewPager2 = findViewById<ViewPager2>(R.id.view_pager).apply {
            adapter = DemoFragmentStateAdapter(
                this@PhotoViewDemoActivity,
                DemoType.PHOTO_VIEW
            )

            orientation = if (intent.getBooleanExtra(ORIENTATION_KEY, false)) {
                ViewPager2.ORIENTATION_VERTICAL
            } else {
                ViewPager2.ORIENTATION_HORIZONTAL
            }
        }

        findViewById<ImageView>(R.id.arrow_back).apply {
            setOnClickListener {
                viewPager2.currentItem -= 1
            }
        }

        findViewById<ImageView>(R.id.arrow_forward).apply {
            setOnClickListener {
                viewPager2.currentItem += 1
            }
        }

        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                viewPager2.y -= distanceY
                return super.onScroll(e1, e2, distanceX, distanceY)
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                // 速度が一定以上だったら画面を閉じる
                if (velocityY > 8_000) {
                    finish()
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        }
        gestureDetector = GestureDetectorCompat(this, gestureListener)
        viewPager2.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    dismissOrRestore(viewPager2)
                }
            }
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    private fun dismissOrRestore(view: View) {
        // y方向のスワイプ量がスワイプ対象のビューの高さ1/3を超えたら画面を閉じる
        if (view.y < view.height / 3) {
            restoreViewTransform(view)
        } else {
            finish()
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
}
