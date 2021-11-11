package jp.studist.rinon2238.imagescalingteststable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import jp.studist.rinon2238.imagescalingteststable.pager.DemoFragmentStateAdapter

class PhotoViewDemoActivity: AppCompatActivity() {

    companion object {
        private const val ORIENTATION_KEY = "orientation_key"

        fun createIntent(context: Context, doVerticalization: Boolean): Intent {
            return Intent(context, this::class.java).apply {
                putExtra(ORIENTATION_KEY, doVerticalization)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_demo_container)

        findViewById<ViewPager2>(R.id.view_pager).apply {
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
    }
}
