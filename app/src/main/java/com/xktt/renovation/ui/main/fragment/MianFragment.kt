package com.xktt.renovation.ui.main.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import com.google.android.material.tabs.TabLayout
import com.xktt.renovation.R
import com.xktt.renovation.baselibs.base.BaseFragment
import com.xktt.renovation.ui.main.adapter.MainPageAdapter
import kotlinx.android.synthetic.main.fragment_mian.*
import kotlinx.android.synthetic.main.item_tab.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [MianFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MianFragment : BaseFragment() {
    private val fragmentInfos:ArrayList<ChildFragmentInfo> = arrayListOf(
        ChildFragmentInfo(0,"首页",R.drawable.main_tab_community_selector)
        ,ChildFragmentInfo(1,"工作台",R.drawable.main_tab_nominate_selector)
        ,ChildFragmentInfo(2,"我的",R.drawable.main_tab_mine_selector))
    private var nameList = ArrayList<String>()

    override fun attachLayoutRes(): Int = R.layout.fragment_mian

    override fun initView(view: View) {
        val fragments = ArrayList<Fragment>()
        for (info in fragmentInfos){
            fragments.add(TestFragment())
            nameList.add(info.text)
        }
        viewPager.setCanScroll(false)
        viewPager.setSmoothScroll(false)
        viewPager.adapter = MainPageAdapter(fragments, nameList, childFragmentManager)
        viewPager.offscreenPageLimit = fragmentInfos.size
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("TabLayout","选中onTabSelected")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("TabLayout","选中onTabUnselected")
                if (fragmentInfos[0].text == tab?.text.toString())
                tab?.customView?.findViewById<ImageView>(R.id.tab_image)?.isActivated = false
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("TabLayout","选中onTabReselected")
            }

        })

        for (i in fragmentInfos.indices) { // 循环添加自定义的tab
            val tab: TabLayout.Tab? = tabLayout.getTabAt(i)
            tab?.customView = getTabView(i)
        }
    }

    private fun getTabView(position: Int): View? {
        layoutInflater.inflate(R.layout.item_tab, tabLayout, false).apply {
            // View设置属性，注意上面引用的包（import属于你们自己的包路径）
            this.tab_image.setImageResource(fragmentInfos[position].iconResId)
            this.tab_text.text = fragmentInfos[position].text
            if (position == 0){
                this.tab_image.isActivated = true
            }
            return this
        }
    }

    override fun lazyLoad() {

    }

  class ChildFragmentInfo(val index: Int,val text: String,val iconResId: Int)
}