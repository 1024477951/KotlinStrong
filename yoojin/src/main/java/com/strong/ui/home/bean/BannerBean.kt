package com.strong.ui.home.bean

/**
 * created by YooJin.
 * date: 2021/1/11 17:28
 * desc:
 */
open class BannerBean(){
    constructor(pageNo: Int,
                pageSize: Int,
                totalPage: Int,
                totalCount: Int,
                list:MutableList<BannerData>?) : this(){
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
    var list: MutableList<BannerData>? = null

    open class BannerData(){
        constructor(title: String?,
                    sourceUrl: String?) : this(){
            this.title = title
            this.sourceUrl = sourceUrl
        }
        var title: String? = null
        var sourceUrl: String? = null
    }

}