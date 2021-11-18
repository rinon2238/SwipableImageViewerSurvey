package jp.studist.rinon2238.swipableimageviewersurvey.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
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
            return inflater.inflate(
                bundle.getInt(demoTypeKey),
                container,
                false
            ).also {
                val description = it.findViewById<ScrollView>(R.id.photoview_exam_description)
                it.findViewById<PhotoView>(R.id.photoview_exam).apply {
                    attacher.apply {
                        this.maximumScale = 6.0f
                    }

                    this.setOnScaleChangeListener { _, _, _ ->
                        Log.d("DemoImageFragment", "scale: $scale")
                        switchDescriptionVisibility(description,floorFloat(scale) > minimumScale)
                    }

                    this.setOnViewTapListener { _, _, _ ->
                        switchDescriptionVisibility(description,description.visibility == View.VISIBLE)
                    }
                }
            }
        }

        return null
    }

    private fun switchDescriptionVisibility(description: View?, condition: Boolean) {
        if (condition) {
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
