package com.example.aplikasivoucherku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var btnShop: ImageView
    private lateinit var btnProfile: ImageView
    private lateinit var btnhome: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hide title bar
        getSupportActionBar()?.hide()

//        val btnProfile: ImageView =findViewById(R.id.btnProfile)
//        btnProfile.setOnClickListener{
//            val intent= Intent(this,ProfilActivity::class.java)
//            startActivity(intent)
//        }
//
//        val btnShop:ImageView=findViewById(R.id.btnShop)
//        btnShop.setOnClickListener{
//            val intent= Intent(this,MobileActivity::class.java)
//            startActivity(intent)
//        }
        val homeFragment=FragmentHome()
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.container, homeFragment)
            commit()
        }

        loadFragment(homeFragment)

        btnShop = findViewById(R.id.btnShop)
        btnProfile = findViewById(R.id.btnProfile)
        btnhome = findViewById(R.id.btnHome)

        btnProfile.setOnClickListener{
            loadFragment(ProfileFragment())
        }
        btnShop.setOnClickListener{
            loadFragment(MobileFragment())
        }

        btnhome.setOnClickListener{
            loadFragment(FragmentHome())
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()

    }
}