package jp.studist.rinon2238.swipableimageviewersurvey.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.chrisbanes.photoview.PhotoView
import jp.studist.rinon2238.swipableimageviewersurvey.DemoType
import jp.studist.rinon2238.swipableimageviewersurvey.R

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
                it.findViewById<PhotoView>(R.id.photoview_exam).apply {
                    attacher.apply {
                        this.maximumScale = 6.0f
                        this.minimumScale = 0.5f
                        this.setAllowParentInterceptOnEdge(false)
                    }
                }
            }
        }

        return null
    }

}
