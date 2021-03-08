package com.strong.ui.splash.bean


open class SplashBean(){
    constructor(pageNo: Int,
                pageSize: Int,
                totalPage: Int,
                totalCount: Int,
                list:MutableList<SplashData>?) : this(){
        this.pageNo = pageNo
        this.pageSize = pageSize
        this.totalPage = totalPage
        this.totalCount = totalCount
        this.list = list
    }

    var pageNo: Int = 0
    var pageSize: Int = 0
    var totalPage: Int = 0
    var totalCount: Int = 0
    var list: MutableList<SplashData>? = null

    open class SplashData(){
        constructor(title: String?,
                    sourceUrl: String?) : this(){
            this.title = title
            this.sourceUrl = sourceUrl
        }
        var title: String? = null
        var sourceUrl: String? = null
    }

}