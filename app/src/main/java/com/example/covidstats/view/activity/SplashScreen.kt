package com.example.covidstats.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.covidstats.R

//activity for splash screen
class SplashScreen : AppCompatActivity() {

    private lateinit var anim:Animation
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //load animation
        anim = AnimationUtils.loadAnimation(this, R.anim.slide_anim)

        //add animation to views
        imageView = findViewById(R.id.splash_imageview)
        imageView.startAnimation(anim)
        textView = findViewById(R.id.splash_textview)
        textView.startAnimation(anim)

        //after 5 seconds, open main activity
        val intent = Intent(this, MainActivity::class.java)
        Handler(mainLooper).postDelayed(
            {
                startActivity(intent.also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            },
            5000
        )
    }
}