package com.yg.newsproject.ui.home.activity

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.promeg.pinyinhelper.Pinyin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yg.newsproject.R
import com.yg.newsproject.baselibs.base.BaseMvpActivity
import com.yg.newsproject.baselibs.utils.AssetsUtils
import com.yg.newsproject.mvp.contract.CityListContract
import com.yg.newsproject.mvp.presenter.CityListPresenter
import com.yg.newsproject.ui.home.adapter.CityListAdapter
import com.yg.newsproject.ui.home.bean.CityBean
import com.yg.newsproject.ui.home.bean.CityListBean
import com.yg.newsproject.widgets.IndexWord
import kotlinx.android.synthetic.main.activity_city_list.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class CityListActivity : BaseMvpActivity<CityListContract.View, CityListContract.Presenter>(),
    CityListContract.View {
    private lateinit var adapter: CityListAdapter
    private lateinit var searchAdapter: CityListAdapter
    private val searchList = ArrayList<CityBean>()

    override fun createPresenter(): CityListContract.Presenter {
        return CityListPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_city_list
    }

    override fun initView() {
        super.initView()
        img_back.setOnClickListener { finish() }

    }

    override fun start() {
        val cityString = AssetsUtils.getJson(this, "province.json")
        //     数据解析
        val gson = Gson()
        val type = object : TypeToken<MutableList<CityListBean?>?>() {}.type
        val cityList: MutableList<CityListBean> = gson.fromJson(cityString, type)
        val allList = ArrayList<CityBean>()
        val hashSet = HashSet<String>()
        for (city in cityList) {
            if (city.city != null && city.city.size > 0) {
                for (area in city.city) {
                    if (area.name == "重庆市"){
                        area.index = "C"
                    } else {
                        var pinyin = Pinyin.toPinyin(area.name, "")
                        area.index = pinyin.substring(0, 1)
                    }
                    hashSet.add(area.index)
                    allList.add(area)
                }
            }
        }
        val pyList = ArrayList(hashSet)
        //对字母排序
        Collections.sort(pyList, object : Comparator<String?> {
            override fun compare(lhs: String?, rhs: String?): Int {
                return if (lhs == "#") {
                    1
                } else (if (rhs == "#") {
                    -1
                } else {
                    lhs!!.compareTo(rhs.toString())
                })
            }

        })
        //对城市排序
        Collections.sort(allList, object : Comparator<CityBean?> {

            override fun compare(lhs: CityBean?, rhs: CityBean?): Int {
                return if (lhs?.index.equals("#") && !rhs?.index.equals("#")) {
                    1
                } else if (!lhs?.index.equals("#") && rhs?.index.equals("#")) {
                    -1
                } else lhs?.index!!.compareTo(rhs?.index.toString())
            }
        })

        indexWord.initpaint(pyList)
        val lp = indexWord.layoutParams
        lp.height = ((pyList.size * indexWord.getWidth() / 1.8).toInt())
        indexWord.layoutParams = lp
        indexWord.invalidate()
        indexWord.setIndexPressWord(object : IndexWord.IndexPressWord {
            override fun setIndexPressWord(word: String?) {
                getWord(word)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CityListAdapter(R.layout.item_city_list)
        recyclerView.adapter = adapter
        //去除相同拼音字母
        var pinyin = ""
        for (bean in allList) {
            if (pinyin == bean.index) {
                bean.index = ""
            } else {
                pinyin = bean.index
            }
        }
        adapter.data = allList

        //搜索
        recyclerSearch.layoutManager = LinearLayoutManager(this)
        searchAdapter = CityListAdapter(R.layout.item_city_list)
        recyclerSearch.adapter = searchAdapter
        et_city.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchCity(s.toString())
            }
        })
    }

    private fun searchCity(text:String) {
        searchAdapter.data.clear()
        searchList.clear()
        if (TextUtils.isEmpty(text)){
            recyclerSearch.visibility = View.GONE
        } else{
            val list = adapter.data
            for (city in list){
                if (city.name.contains(text)){
                    city.index = ""
                    searchList.add(city)
                }
            }
            searchAdapter.data = searchList
            searchAdapter.notifyDataSetChanged()
            recyclerSearch.visibility = View.VISIBLE
        }
    }

    private fun getWord(word: String?) {
        try {
            val allList = adapter.data
            for (i in allList.indices) {
                var pinyin = allList[i].index
                if (word == pinyin){
                    val manager = recyclerView.layoutManager as LinearLayoutManager
                    val firstItem: Int = manager.findFirstVisibleItemPosition()
                    val lastItem: Int = manager.findLastVisibleItemPosition()
                    if (i <= firstItem) {
                        recyclerView.scrollToPosition(i)
                    } else if (i <= lastItem) {
                        val view: View = recyclerView.getChildAt(i - firstItem)
                        if (view != null) {
                            val top = view.top
                            recyclerView.scrollBy(0, top)
                        }
                    } else {
                        recyclerView.scrollToPosition(i)
                    }
                    break
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getRecordsSuccess(any: Any) {

    }

}