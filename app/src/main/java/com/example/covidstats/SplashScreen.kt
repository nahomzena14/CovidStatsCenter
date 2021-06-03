package com.example.covidstats

import android.content.Intent
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.covidstats.view.MainActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var anim:Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val intent = Intent(this, MainActivity::class.java)
        Handler(mainLooper).postDelayed(
            {
                startActivity(intent.also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            },
            1000
        )
    }
}