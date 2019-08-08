package com.kotlinstrong.bean

import java.io.Serializable

data class ArticleList( val offset: Int,
                        val size: Int,
                        val total: Int,
                        val pageCount: Int,
                        val curPage: Int,
                        val over: Boolean,
                        val datas: MutableList<Article>):Serializable