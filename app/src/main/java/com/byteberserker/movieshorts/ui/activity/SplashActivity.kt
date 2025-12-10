package com.byteberserker.movieshorts.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : BaseActivity() {

    private val splashDuration = 1000L // 1 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // No need to set content view - theme handles the splash screen
        
        // Navigate to HomeActivity after delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, splashDuration)
    }
}
