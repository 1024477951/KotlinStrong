package com.kotlinstrong.splash

import android.os.Bundle
import android.os.Handler
import com.kotlinstrong.R
import com.kotlinstrong.main.MainActivity
import com.kotlinstrong.stronglib.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun layoutId() = R.layout.activity_splash

    override fun initData(bundle: Bundle?) {
        Handler().postDelayed({
            MainActivity.startMainActivity()
            finish()
        }, 500)
    }

    override fun onDestroy() {
        super.onDestroy()
        window.setBackgroundDrawable(null)
    }


}