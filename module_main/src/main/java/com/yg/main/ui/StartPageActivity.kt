package com.yg.main.ui

import android.os.CountDownTimer
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.BarUtils
import com.yg.common.router.ARouterNavigator
import com.yg.lib_core.http.ApiRetrofit
import com.yg.main.R
import com.yg.newsproject.baselibs.base.BaseActivity
import com.yg.newsproject.baselibs.constant.Constant
import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.utils.DateUtils
import com.yg.newsproject.baselibs.utils.GlideUtil
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.listener.OnPageChangeListener
import org.jetbrains.anko.find
import java.util.*
import kotlin.collections.ArrayList


class StartPageActivity : BaseActivity(), OnPageChangeListener {
    private lateinit var banner: Banner<Int, BannerImageAdapter<Int>>
    private lateinit var tvAd :TextView
    private lateinit var adapter : ImageAdapter
    private lateinit var imgList : MutableList<Any>
    private lateinit var countDownTimer: CountDownTimer
    private var loopTime = 2000L
    private val baseTime = 1000L
    private val startTime = 3000L
    private var adTime = 0L

    override fun attachLayoutRes(): Int {
        return R.layout.activity_start_page
    }

    override fun initView() {
        BarUtils.transparentStatusBar(this)
        banner = find(R.id.banner)
        tvAd = find(R.id.tv_ad)
        imgList = ArrayList()
        adapter = ImageAdapter(imgList as ArrayList<Any>)
        banner.addBannerLifecycleObserver(this)
            .setAdapter(adapter)
        banner.addOnPageChangeListener(this)
        tvAd.setOnClickListener {
            if (countDownTimer != null){
                countDownTimer.cancel()
                ARouterNavigator().navigateHomeActivity(this@StartPageActivity)
                finish()
            }
        }
    }

    override fun start() {
        ApiRetrofit.service.getStartPageAd("7", Constant.DEVICE_TYPE).ss(onSuccess = {
            if (it.status == 200 && !it.data.isNullOrEmpty() && it.data.size > 0){
                val data = it.data[0]
                if (!DateUtils.isThanTarget(data.startDate) && DateUtils.isThanTarget(data.endDate)){
                    imgList.addAll(data.titleFilePath)
                    loopTime = data.startDisplayTime[0].toLong() * baseTime
                } else {
                    imgList.add(R.mipmap.icon_start_page)
                }
            } else {
                imgList.add(R.mipmap.icon_start_page)
            }
            startTimer()
        },onError = {
            imgList.add(R.mipmap.icon_start_page)
            startTimer()
        })
    }
    private fun startTimer(){
        tvAd.isVisible = true
        adTime = loopTime * imgList.size / baseTime
        tvAd.text = "${adTime}s"
        banner.setLoopTime(loopTime).start()
        adapter.notifyDataSetChanged()
        countDownTimer = object : CountDownTimer(adTime * baseTime,baseTime) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished > startTime){
                    tvAd.isClickable = false
                    tvAd.text = "${millisUntilFinished / baseTime}s"
                } else {
                    tvAd.text = "跳转${millisUntilFinished / baseTime}s"
                    tvAd.isClickable = true
                }
            }

            override fun onFinish() {
                tvAd.text = "跳转0s"
                ARouterNavigator().navigateHomeActivity(this@StartPageActivity)
                finish()
            }

        }.start()
    }

    class ImageAdapter(list: ArrayList<Any>) : BannerAdapter<Any, ImageHolder>(list) {
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

        override fun onBindView(holder: ImageHolder?, data: Any?, position: Int, size: Int) {
            var view = holder?.itemView as ImageView
            if (data is String){
                GlideUtil.loadImage(view.context,view,data,R.mipmap.icon_start_page,R.mipmap.icon_start_page,false)
            } else {
                data?.let { view.setImageResource(it as Int) }
            }

        }

    }

    class ImageHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView)

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.d("PageScrolled", position.toString())
    }

    override fun onPageSelected(position: Int) {
        Log.d("PageSelected", position.toString())
        if (position == imgList.size - 2) {
            banner.isAutoLoop(false)
            banner.stop()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        Log.d("PageSelectedState", state.toString())
    }
}