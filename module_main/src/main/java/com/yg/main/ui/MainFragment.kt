package com.yg.main.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.location.*
import com.blankj.utilcode.util.PermissionUtils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.yg.common.adapter.MainPageAdapter
import com.yg.common.router.RouterFragmentPath
import com.yg.lib_core.db.database.CommonDatabase
import com.yg.lib_core.db.entity.MainMenuBean
import com.yg.lib_core.http.ApiRetrofit
import com.yg.main.R
import com.yg.main.mvp.contract.MainContract
import com.yg.main.mvp.presenter.MainPresenter
import com.yg.newsproject.baselibs.base.BaseMvpFragment
import com.yg.newsproject.baselibs.config.UserManager
import com.yg.newsproject.baselibs.constant.Constant
import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.utils.GlideUtil
import com.yg.newsproject.baselibs.utils.NetWorkUtil
import com.yg.newsproject.baselibs.utils.SettingUtil
import com.yg.newsproject.baselibs.utils.ToastUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_tab.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : BaseMvpFragment<MainContract.View, MainContract.Presenter>(),
    MainContract.View {
    private var nameList = ArrayList<String>()
    private var fragments = ArrayList<Fragment>()
    private val menuList = mutableListOf<MainMenuBean>()
    private val SDK_PERMISSION_REQUEST = 127
    private lateinit var mOption: LocationClientOption
    private lateinit var client: LocationClient

    override fun createPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_main
    override fun lazyLoad() {
//        mPresenter?.findMainMenu()
        if (NetWorkUtil.isConnected()) {
            ApiRetrofit.service.findMainMenu(Constant.DEVICE_TYPE).ss(onSuccess = {
                if (it?.data != null && it.data.size > 0) {
                    menuList.clear()
                    menuList.addAll(it.data)
                    for (i in it.data.indices) {
                        val bean = it.data[i]
                        nameList.add(bean.name.toString())
                        if (i != it.data.size - 1){
                            fragments.add(
                                ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME).withString("type",bean.type.toString())
                                    .navigation() as Fragment
                            )
                        } else {
                            fragments.add(
                                ARouter.getInstance().build(RouterFragmentPath.User.PAGER_USER)
                                    .navigation() as Fragment
                            )
                        }
                    }
                }
                viewPager.adapter?.notifyDataSetChanged()
                viewPager.offscreenPageLimit = menuList.size
                for (i in fragments.indices) {
                    val bean = it.data[i]
                    val tab: TabLayout.Tab? = tabLayout.getTabAt(i)
                    tab?.customView = getTabView(i, bean)
                }
                Thread {
                    val dao = CommonDatabase.getInstance(requireContext()).mainMenuDao()
                    dao.deleteAll()
                    dao.insertMainMenuList(menuList)
                }.start()
            }, onError = {
                getDbMainMenu()
            })
        } else {
            getDbMainMenu()
        }
    }

    private fun getDbMainMenu(){
        Observable.just(menuList).flatMap{
            val list = CommonDatabase.getInstance(requireContext()).mainMenuDao().getMainMenuList()
            it.clear()
            it.addAll(list)
            return@flatMap Observable.just(it)
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                for (i in it.indices) {
                    val bean = it[i]
                    nameList.add(bean.name.toString())
                    if (i == 0){
                        fragments.add(
                            ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME)
                                .navigation() as Fragment
                        )
                    } else {
                        fragments.add(
                            ARouter.getInstance().build(RouterFragmentPath.User.PAGER_USER)
                                .navigation() as Fragment
                        )
                    }
                }
                viewPager.adapter?.notifyDataSetChanged()
                viewPager.offscreenPageLimit = it.size
                for (i in fragments.indices) {
                    val bean = it[i]
                    val tab: TabLayout.Tab? = tabLayout.getTabAt(i)
                    tab?.customView = getTabView(i, bean)
                }
            }, {
                it.printStackTrace()
            })
    }

    override fun initView(view: View) {
        if (!PermissionUtils.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getPersimmions()
        } else {
            getLocationClient()
        }
        view.findViewById<AppBarLayout>(R.id.appbarLayout)
            .setBackgroundColor(SettingUtil.getColor())
//        activity?.let { BarUtils.setStatusBarLightMode(it,true) }
        viewPager.setCanScroll(false)
        viewPager.setSmoothScroll(false)
        viewPager.adapter = MainPageAdapter(fragments, nameList, childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Log.d("TabLayout","选中onTabSelected")
                val textView = tab?.customView?.findViewById<TextView>(R.id.tab_text)
                val imgView = tab?.customView?.findViewById<ImageView>(R.id.tab_image)
                if (textView != null)
                    textView?.setTextColor(SettingUtil.getColor())
                if (imgView != null)
                    context?.let {
                        GlideUtil.loadImage(
                            it,
                            imgView!!,
                            getThemeImage(tab?.position)
                        )
                    }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                Log.d("TabLayout","选中onTabUnselected")
                val textView = tab?.customView?.findViewById<TextView>(R.id.tab_text)
                val imgView = tab?.customView?.findViewById<ImageView>(R.id.tab_image)
                if (textView != null)
                    textView?.setTextColor(context!!.resources.getColor(R.color.text_grey))
                if (imgView != null)
                    context?.let {
                        GlideUtil.loadImage(
                            it,
                            imgView!!,
                            menuList[tab?.position!!].imgUrl!!
                        )
                    }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                Log.d("TabLayout","选中onTabReselected")
            }

        })
    }

    private fun getTabView(position: Int, bean: MainMenuBean): View? {
        layoutInflater.inflate(R.layout.item_tab, tabLayout, false).apply {
            // View设置属性，注意上面引用的包（import属于你们自己的包路径）
            this.tab_text.text = bean.name
            if (position == 0) {
                this.tab_text.setTextColor(SettingUtil.getColor())
                GlideUtil.loadImage(context, this.tab_image, bean.imgUrl!!)
                if (!bean.subjects.isNullOrEmpty() && bean.subjects!!.size > 0) {
                    GlideUtil.loadImage(context, this.tab_image, getThemeImage(position))
                } else {
                    GlideUtil.loadImage(context, this.tab_image, bean.imgUrl!!)
                }
            } else {
                GlideUtil.loadImage(context, this.tab_image, bean.imgUrl!!)
            }
            return this
        }
    }

    private fun getThemeImage(position: Int): String {
        val bean = menuList[position]
        val name = when (SettingUtil.getColorType()) {
            0 -> "朝霞映日"
            1 -> "流光溢彩"
            2 -> "紫气东来"
            else -> "蓝色的梦"
        }
        for (data in bean.subjects!!) {
            if (data.name == name) {
                return data.subjectImgUrl!!
            }
        }
        return ""
    }


    private fun getLocationClient(): LocationClient {
        client = LocationClient(context)
        client.locOption = getDefaultLocationClientOption()
        client.registerLocationListener(mListener)
        client.start()
        return client
    }

    private fun getDefaultLocationClientOption(): LocationClientOption {
        mOption = LocationClientOption()
        mOption.locationMode =
            LocationClientOption.LocationMode.Hight_Accuracy // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll") // 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setIsNeedAddress(true) // 可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true) // 可选，设置是否需要地址描述
        mOption.setIsNeedLocationDescribe(true) // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        mOption.setIsNeedLocationPoiList(true) // 可选，默认false，设置是否需要POI结果，可以在BDLocation
        mOption.isOpenGps = true // 可选，默认false，设置是否开启Gps定位
        return mOption
    }

    private fun getPersimmions() {
//        if (!PermissionUtils.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)){
//            PermissionUtils.permission(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
//        } else{
//            ToastUtils.showToast("已授权")
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = mutableListOf<String>()
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (context?.let {
                    checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (context?.let {
                    checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n"
            }
            if (permissions.size > 0) {
                requestPermissions(permissions.toTypedArray(), SDK_PERMISSION_REQUEST)
            }
        }
    }

    private fun addPermission(permissionsList: MutableList<String>, permission: String): Boolean {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        return if (context?.let {
                checkSelfPermission(
                    it,
                    permission
                )
            } != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                true
            } else {
                permissionsList.add(permission)
                false
            }
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SDK_PERMISSION_REQUEST) {
            getLocationClient()
        } else {
            ToastUtils.showToast("请打开定位权限，不开启某些功能将无法正常工作！")
        }
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private val mListener: BDAbstractLocationListener = object : BDAbstractLocationListener() {
        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        override fun onReceiveLocation(location: BDLocation) {

            // TODO Auto-generated method stub
            if (null != location && location.locType != BDLocation.TypeServerError) {
                val sb = StringBuffer(256)
                sb.append("time : ")
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.time)
                sb.append("\nlocType : ") // 定位类型
                sb.append(location.locType)
                sb.append("\nlocType description : ") // *****对应的定位类型说明*****
                sb.append(location.locTypeDescription)
                sb.append("\nlatitude : ") // 纬度
                sb.append(location.latitude)
                sb.append("\nlongtitude : ") // 经度
                sb.append(location.longitude)
                sb.append("\nradius : ") // 半径
                sb.append(location.radius)
                sb.append("\nCountryCode : ") // 国家码
                sb.append(location.countryCode)
                sb.append("\nProvince : ") // 获取省份
                sb.append(location.province)
                sb.append("\nCountry : ") // 国家名称
                sb.append(location.country)
                sb.append("\ncitycode : ") // 城市编码
                sb.append(location.cityCode)
                sb.append("\ncity : ") // 城市
                sb.append(location.city)
                sb.append("\nDistrict : ") // 区
                sb.append(location.district)
                sb.append("\nTown : ") // 获取镇信息
                sb.append(location.town)
                sb.append("\nStreet : ") // 街道
                sb.append(location.street)
                sb.append("\naddr : ") // 地址信息
                sb.append(location.addrStr)
                sb.append("\nStreetNumber : ") // 获取街道号码
                sb.append(location.streetNumber)
                sb.append("\nUserIndoorState: ") // *****返回用户室内外判断结果*****
                sb.append(location.userIndoorState)
                sb.append("\nDirection(not all devices have value): ")
                sb.append(location.direction) // 方向
                sb.append("\nlocationdescribe: ")
                sb.append(location.locationDescribe) // 位置语义化信息
                sb.append("\nPoi: ") // POI信息
                if (location.poiList != null && !location.poiList.isEmpty()) {
                    for (i in location.poiList.indices) {
                        val poi = location.poiList[i] as Poi
                        sb.append("poiName:")
                        sb.append(poi.name + ", ")
                        sb.append("poiTag:")
                        sb.append(
                            """
                            ${poi.tags}
                           
                            """.trimIndent()
                        )
                    }
                }
                if (location.poiRegion != null) {
                    sb.append("PoiRegion: ") // 返回定位位置相对poi的位置关系，仅在开发者设置需要POI信息时才会返回，在网络不通或无法获取时有可能返回null
                    val poiRegion = location.poiRegion
                    sb.append("DerectionDesc:") // 获取POIREGION的位置关系，ex:"内"
                    sb.append(poiRegion.derectionDesc + "; ")
                    sb.append("Name:") // 获取POIREGION的名字字符串
                    sb.append(poiRegion.name + "; ")
                    sb.append("Tags:") // 获取POIREGION的类型
                    sb.append(poiRegion.tags + "; ")
                    sb.append("\nSDK版本: ")
                }

                if (location.locType == BDLocation.TypeGpsLocation) { // GPS定位结果
                    sb.append("\nspeed : ")
                    sb.append(location.speed) // 速度 单位：km/h
                    sb.append("\nsatellite : ")
                    sb.append(location.satelliteNumber) // 卫星数目
                    sb.append("\nheight : ")
                    sb.append(location.altitude) // 海拔高度 单位：米
                    sb.append("\ngps status : ")
                    sb.append(location.gpsAccuracyStatus) // *****gps质量判断*****
                    sb.append("\ndescribe : ")
                    sb.append("gps定位成功")
                } else if (location.locType == BDLocation.TypeNetWorkLocation) { // 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) { // *****如果有海拔高度*****
                        sb.append("\nheight : ")
                        sb.append(location.altitude) // 单位：米
                    }
                    sb.append("\noperationers : ") // 运营商信息
                    sb.append(location.operators)
                    sb.append("\ndescribe : ")
                    sb.append("网络定位成功")
                } else if (location.locType == BDLocation.TypeOffLineLocation) { // 离线定位结果
                    sb.append("\ndescribe : ")
                    sb.append("离线定位成功，离线定位结果也是有效的")
                } else if (location.locType == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ")
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因")
                } else if (location.locType == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ")
                    sb.append("网络不同导致定位失败，请检查网络是否通畅")
                } else if (location.locType == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ")
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机")
                }
                UserManager.get().setUserCity(location.city)
                UserManager.get().setUserLocation(location.addrStr)
                UserManager.get().setUserLatitude(location.latitude.toString())
                UserManager.get().setUserLongtitude(location.longitude.toString())
                Log.d("location", sb.toString())
            }
        }

        override fun onConnectHotSpotMessage(s: String, i: Int) {
            super.onConnectHotSpotMessage(s, i)
        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * @param locType 当前定位类型
         * @param diagnosticType 诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */
        override fun onLocDiagnosticMessage(
            locType: Int,
            diagnosticType: Int,
            diagnosticMessage: String
        ) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage)
            if (locType == BDLocation.TypeNetWorkLocation) {
                if (diagnosticType == 1) {
                    ToastUtils.showToast("网络定位成功，没有开启GPS，建议打开GPS会更好")
                } else if (diagnosticType == 2) {
                    ToastUtils.showToast("网络定位成功，没有开启Wi-Fi，建议打开Wi-Fi会更好")
                }
            } else if (locType == BDLocation.TypeOffLineLocationFail) {
                if (diagnosticType == 3) {
                    ToastUtils.showToast("定位失败，请您检查您的网络状态")
                }
            } else if (locType == BDLocation.TypeCriteriaException) {
                when (diagnosticType) {
                    4 -> {
                        ToastUtils.showToast("定位失败，无法获取任何有效定位依据")
                    }
                    5 -> {
                        ToastUtils.showToast("定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位")
                    }
                    6 -> {
                        ToastUtils.showToast("定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试")
                    }
                    7 -> {
                        ToastUtils.showToast("定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试")
                    }
                    9 -> {
                        ToastUtils.showToast("定位失败，无法获取任何有效定位依据")
                    }
                }
            } else if (locType == BDLocation.TypeServerError) {
                if (diagnosticType == 8) {
                    ToastUtils.showToast("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限")
                }
            }
        }
    }

    override fun onStop() {
        client.unRegisterLocationListener(mListener)
        client.stop()
        super.onStop()
    }

    class ChildFragmentInfo(val index: Int, val text: String, val iconResId: Int)

    override fun getMainMenuSuccess(data: MutableList<MainMenuBean>?) {

    }

}