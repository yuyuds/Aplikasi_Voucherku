package com.example.aplikasivoucherku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    //splash screen
    private val SPLASH_TIME_OUT:Long =2000 //2second
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //hide title bar
        getSupportActionBar()?.hide()

        Handler() .postDelayed( {
            startActivity(Intent(this,Splash2Activity::class.java))

            finish()
        }, SPLASH_TIME_OUT)
    }
}