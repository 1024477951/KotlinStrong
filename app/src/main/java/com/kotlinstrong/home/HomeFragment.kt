package com.kotlinstrong.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.R
import com.kotlinstrong.base.TabFragment
import com.kotlinstrong.bean.Article
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.main.MainViewModel
import com.kotlinstrong.BR
import com.kotlinstrong.stronglib.base.BaseAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : TabFragment<MainViewModel>() {

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun layoutId() = R.layout.fragment_home

    private var mAdapter: BaseAdapter<Article>? = null

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
//        mViewModel.getArticleList(0)
        Log.e("===>",tag)
        mAdapter = BaseAdapter(context, BR.data, R.layout.item_article)
        recyclerView.adapter = mAdapter
    }

    override fun modelObserve() {
        mViewModel.apply {
            mArticleList.observe(this@HomeFragment, Observer {
                setArticles(it)
            })
        }
    }

    private fun setArticles(articleList: ArticleList) {
        Log.e("===>","success "+articleList.size)
        mAdapter!!.setNewList(articleList.datas)
    }
}