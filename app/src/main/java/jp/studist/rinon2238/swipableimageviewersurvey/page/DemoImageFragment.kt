package jp.studist.rinon2238.swipableimageviewersurvey.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.studist.rinon2238.swipableimageviewersurvey.DemoType

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
        arguments?.let {
            return inflater.inflate(
                it.getInt(demoTypeKey),
                container,
                false
            )
        }

        return null
    }

}
