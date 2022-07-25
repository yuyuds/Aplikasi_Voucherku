package com.example.aplikasivoucherku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        //hide title bar
        getSupportActionBar()?.hide()

        val btnBack:ImageView=findViewById(R.id.btnBack)
        btnBack.setOnClickListener{
//            val fragment: Fragment = FragmentHome()
//            val activity: AppCompatActivity = it.context as AppCompatActivity
//
//            activity.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()

            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }
    }
}