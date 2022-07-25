package com.example.aplikasivoucherku

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasivoucherku.MainActivity
import com.example.aplikasivoucherku.R
import com.example.aplikasivoucherku.model.VoucherModel
import com.example.aplikasivoucherku.DatabaseHelper

class FormEditActivity : AppCompatActivity() {

    //object dr image view
    lateinit var image: ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_edit)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance button
        image = findViewById(R.id.imageVoucher)
        val textId : EditText = findViewById(R.id.voucherId)
        val textName : EditText = findViewById(R.id.voucherName)
        val textPrice : EditText = findViewById(R.id.voucherPrice)
        val btnAddImage : Button = findViewById(R.id.buttonAddImage)
        val btnSaveVoucher : Button = findViewById(R.id.buttonSaveVoucher)

        val databaseHelper = DatabaseHelper(this)

        val extras = intent.extras
        if (extras != null) {
            val voucherId = extras.getInt("id")

            if (voucherId != null) {
                val result: ArrayList<VoucherModel> = databaseHelper.getVoucher(voucherId.toString())

                textId.setText(result[0].id.toString())
                textName.setText(result[0].name)
                textPrice.setText(result[0].price.toString())
                image.setImageBitmap(result[0].image)

            }


        }

        btnSaveVoucher.setOnClickListener {
            val voucherId = extras?.getInt("id")
            val databaseHelper = DatabaseHelper(this)

            val id : Int = textId.text.toString().toInt()
            val name : String = textName.text.toString().trim()
            val price : Int = textPrice.text.toString().toInt()
            val bitmapDrawable : BitmapDrawable = image.drawable as BitmapDrawable
            val bitmap : Bitmap = bitmapDrawable.bitmap

            val voucherModel = VoucherModel(id,name,price, bitmap)
            if (voucherId != null) {
                databaseHelper.updateVoucher(voucherModel, voucherId.toString())
            }
            val intent = Intent( this, MainActivity::class.java)
            startActivity(intent)

        }


    }
}