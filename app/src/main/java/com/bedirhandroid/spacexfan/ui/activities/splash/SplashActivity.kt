package com.bedirhandroid.spacexfan.ui.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.ui.activities.navdrawer.NavDrawerActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val gif = findViewById<LottieAnimationView>(R.id.anim_lottie)
        gif.playAnimation()
        nextScreen()
    }

    private fun nextScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, NavDrawerActivity::class.java).also {
                startActivity(it)
            }
        },2000)
    }
}