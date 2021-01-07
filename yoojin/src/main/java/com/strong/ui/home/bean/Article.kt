package com.strong.ui.home.bean


open class Article(){
    constructor(id: Int,
                originId: Int,
                title: String,
                chapterId: Int,
                chapterName: String,
                envelopePic: String,
                link: String,
                author: String,
                origin: String,
                publishTime: Long,
                zan: Int,
                desc: String,
                visible: Int,
                niceDate: String,
                courseId: Int,
                collect: Boolean,
                apkLink:String,
                projectLink:String,
                superChapterId:Int,
                superChapterName:String,
                type:Int,
                fresh:Boolean) : this(){
        this.id = id
        this.originId = originId
        this.title = title
        this.chapterId = chapterId
        this.chapterName = chapterName
        this.envelopePic = envelopePic
        this.link = link
        this.author = author
        this.origin = origin
        this.publishTime = publishTime
        this.zan = zan
        this.desc = desc
        this.visible = visible
        this.niceDate = niceDate
        this.courseId = courseId
        this.collect = collect
        this.apkLink = apkLink
        this.projectLink = projectLink
        this.superChapterId = superChapterId
        this.superChapterName = superChapterName
        this.type = type
        this.fresh = fresh
    }

    constructor(id: Int, list: MutableList<String>) : this(){
        this.id = id
        this.list = list

        this.title = ""
        this.chapterName = ""
        this.envelopePic = ""
        this.link = ""
        this.author = ""
        this.origin = ""
        this.desc = ""
        this.niceDate = ""
        this.apkLink = ""
        this.projectLink = ""
        this.superChapterName = ""
    }

    var id: Int = 0
    var originId: Int = 0
    var title: String? = null
    var chapterId: Int = 0
    var chapterName: String? = null
    var envelopePic: String? = null
    var link: String? = null
    var author: String? = null
    var origin: String? = null
    var publishTime: Long = 0
    var zan: Int = 0
    var desc: String? = null
    var visible: Int = 0
    var niceDate: String? = null
    var courseId: Int = 0
    var collect: Boolean = false
    var apkLink:String? = null
    var projectLink:String? = null
    var superChapterId:Int = 0
    var superChapterName:String? = null
    var type:Int = 0
    var fresh:Boolean = false
    var list: MutableList<String>? = null
}