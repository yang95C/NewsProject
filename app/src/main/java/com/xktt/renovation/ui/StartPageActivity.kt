package com.xktt.renovation.ui

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.xktt.renovation.R
import com.xktt.renovation.baselibs.base.BaseActivity
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.listener.OnPageChangeListener
import org.jetbrains.anko.find


class StartPageActivity : BaseActivity(), OnPageChangeListener {
    private lateinit var banner: Banner<Int, BannerImageAdapter<Int>>

    override fun attachLayoutRes(): Int {
        return R.layout.activity_start_page
    }

    override fun initView() {
        banner = find(R.id.banner)
        val list = ArrayList<Int>()
        list.add(R.mipmap.icon_start_page)
        list.add(R.mipmap.ic_launcher)
        val adapter = ImageAdapter(list)
        banner.addBannerLifecycleObserver(this)
            .setAdapter(adapter)
            .setLoopTime(1500)
//            .setIndicator(CircleIndicator(this))
        banner.addOnPageChangeListener(this)
    }

    override fun start() {

    }

    class ImageAdapter(list: ArrayList<Int>) : BannerAdapter<Int, ImageHolder>(list) {
        override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
            val imageView = ImageView(parent?.context)
            //注意，必须设置为match_parent，这个是viewpager2强制要求的
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.layoutParams = params
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            return ImageHolder(imageView)
        }

        override fun onBindView(holder: ImageHolder?, data: Int?, position: Int, size: Int) {
            var view = holder?.itemView as ImageView
            data?.let { view.setImageResource(it) }
        }

    }

    class ImageHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView)

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.d("PageScrolled", position.toString())
    }

    override fun onPageSelected(position: Int) {
        Log.d("PageSelected", position.toString())
        if (position == 1) {
            banner.isAutoLoop(false)
            banner.postDelayed(Runnable {
                val intent = Intent(this,MainActivity().javaClass)
                startActivity(intent)
                finish()
            },1500)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        Log.d("PageSelectedState", state.toString())
    }
}