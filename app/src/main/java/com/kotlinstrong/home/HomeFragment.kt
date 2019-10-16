package com.kotlinstrong.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.R
import com.kotlinstrong.base.TabFragment
import com.kotlinstrong.bean.Article
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.main.MainViewModel
import com.kotlinstrong.BR
import com.kotlinstrong.option.OptionsActivity
import com.kotlinstrong.stronglib.base.BaseAdapter
import com.kotlinstrong.stronglib.listener.Function
import com.kotlinstrong.stronglib.listener.LongFunction
import com.kotlinstrong.stronglib.listener.ViewMap
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_head_ads.*

class HomeFragment : TabFragment<MainViewModel>() ,OnRefreshLoadMoreListener{

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun layoutId() = R.layout.fragment_home

    private var mAdapter: BaseAdapter<Article>? = null

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
        LogUtils.d("==>",tag)
        mAdapter = BaseAdapter(context, BR.data, object : ViewMap<Article> {
            override fun layoutId(t: Article): Int {
                return when {
                    t.id == -1 -> R.layout.layout_head_ads
                    t.envelopePic!!.isNotEmpty() -> R.layout.item_text
                    else -> R.layout.item_article
                }
            }
        })
        recyclerView.adapter = mAdapter
        mAdapter!!.addEvent(BR.click, object : Function<Article> {
            override fun call(view: View, t: Article) {
                ActivityUtils.startActivity(OptionsActivity::class.java)
            }
        })
        mAdapter!!.addLongEvent(BR.longClick, object : LongFunction<Article> {
            override fun call(view: View, t: Article): Boolean {
                ToastUtils.showShort("longClick "+t.title)
                return true
            }
        })
        refreshLayout.setOnRefreshLoadMoreListener(this)
        mAdapter!!.setNewList(ArrayList())
        mViewModel.getArticleList(0)
    }

    override fun modelObserve() {
        mViewModel.apply {
            mArticleList.observe(this@HomeFragment, Observer {
                setArticles(it)
            })
        }
    }

    private fun setArticles(articleList: ArticleList) {
//        LogUtils.e(tag,"success "+articleList.size)
        var article = Article(-1,mViewModel.getAdsList())
        articleList.datas.add(0,article)
        mAdapter!!.setList(articleList.datas,true)
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
        pager_ads.onDestory()
    }

}