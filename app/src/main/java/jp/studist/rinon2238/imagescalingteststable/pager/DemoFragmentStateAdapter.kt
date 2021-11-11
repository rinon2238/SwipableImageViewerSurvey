package jp.studist.rinon2238.imagescalingteststable.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import jp.studist.rinon2238.imagescalingteststable.DemoType
import jp.studist.rinon2238.imagescalingteststable.page.DemoImageFragment

class DemoFragmentStateAdapter(
    parent: FragmentActivity,
    private val demoType: DemoType
) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return DemoImageFragment.createMe(demoType)
    }
}