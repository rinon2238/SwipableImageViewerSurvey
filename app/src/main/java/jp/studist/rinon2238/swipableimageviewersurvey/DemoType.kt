package jp.studist.rinon2238.swipableimageviewersurvey

import androidx.annotation.LayoutRes

enum class DemoType(@LayoutRes val resId: Int) {
    ZOOMAGE(R.layout.fragment_page_zoomageview),
    PHOTO_VIEW(R.layout.fragment_page_photoview)
}
