package com.example.aplikasivoucherku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PembelianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembelian)

        //hide title bar
        getSupportActionBar()?.hide()
    }
}