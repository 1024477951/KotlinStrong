package com.kotlinstrong.databinding.textview

import android.widget.TextView
import androidx.databinding.BindingAdapter

class TextViewBinding {
    companion object {
        val TAG: String = "TextViewBinding"

        @BindingAdapter("article_title")
        @JvmStatic
        fun setArticleTitle(textView: TextView, title: String){
            if (title != null) {
                textView.text = title
            }else{
                textView.text = ""
            }
        }
    }
}