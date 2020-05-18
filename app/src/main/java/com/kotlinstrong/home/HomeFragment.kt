package com.kotlinstrong.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.kotlinstrong.R
import com.kotlinstrong.base.TabFragment
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.main.MainViewModel
import com.kotlinstrong.home.item.ArticleContent1BindItem
import com.kotlinstrong.home.item.ArticleContent2BindItem
import com.kotlinstrong.home.item.ArticleHeadBindItem
import com.kotlinstrong.option.OptionsActivity
import com.kotlinstrong.stronglib.base.BaseAdapter
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.utils.aspect.MyAnnotationLogin
import com.kotlinstrong.utils.aspect.MyAnnotationOnclick
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_error.view.*
import kotlinx.android.synthetic.main.layout_head_ads.*

class HomeFragment : TabFragment<MainViewModel>() ,OnRefreshLoadMoreListener{

    override fun layoutId() = R.layout.fragment_home

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    private var mAdapter: BaseAdapter<BaseBindItem>? = null
    private var root: View? = null

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
        LogUtils.d("==>",tag)
        mAdapter = BaseAdapter()
        recyclerView.adapter = mAdapter

        refreshLayout.setOnRefreshLoadMoreListener(this)
        mViewModel.getArticleList(0)
        root = view_stub.inflate().tv_val
    }

    override fun modelObserve() {
        mViewModel.apply {
            mArticleList.observe(this@HomeFragment, Observer {
                setArticles(it!!)
            })
        }
    }

    @MyAnnotationOnclick
    fun openActivity(){
        ActivityUtils.startActivity(OptionsActivity::class.java)
    }

    @MyAnnotationLogin
    private fun openDetail(){
        ActivityUtils.startActivity(OptionsActivity::class.java)
    }

    private fun setArticles(articleList: ArticleList) {
        root!!.visibility = View.GONE

        val list: MutableList<BaseBindItem> = ArrayList()
        list.add(ArticleHeadBindItem(mViewModel.getAdsList()))
        for (article in articleList.datas){
            if (article.envelopePic!!.isNotEmpty()){
                list.add(ArticleContent1BindItem(article))
            }else{
                list.add(ArticleContent2BindItem(article))
            }
        }

        mAdapter!!.setNewList(list)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        refreshLayout.finishLoadMore()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.getArticleList(0)
        refreshLayout.finishRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(pager_ads != null){
            pager_ads.onDestory()
        }
    }

}