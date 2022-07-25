package com.example.aplikasivoucherku

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.aplikasivoucherku.R
import com.example.aplikasivoucherku.model.VoucherModel

class AddVoucherActivity : AppCompatActivity() {
    //object dr image view
    lateinit var image: ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_voucher)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance button
        image = findViewById(R.id.imageGame)
        val textId : EditText = findViewById(R.id.voucherID)
        val textName : EditText = findViewById(R.id.voucherName)
        val textPrice : EditText = findViewById(R.id.voucherPrice)
        val btnAddImage : Button = findViewById(R.id.buttonAddImage)
        val btnSaveVoucher : Button = findViewById(R.id.buttonSaveVoucher)

        //event button upload (+)
        btnAddImage.setOnClickListener {
            pickImageGalery()
        }

        btnSaveVoucher.setOnClickListener {
            val databaseHelper = DatabaseHelper(this)

            val id : Int = textId.text.toString().toInt()
            val name : String = textName.text.toString().trim()
            val price : Int = textPrice.text.toString().toInt()
            val bitmapDrawable : BitmapDrawable = image.drawable as BitmapDrawable
            val bitmap : Bitmap = bitmapDrawable.bitmap

            val VoucherModel = VoucherModel(id, name,price, bitmap)
            databaseHelper.addVoucher(VoucherModel)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }


    }

    private fun pickImageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        image = findViewById(R.id.imageGame)
        val code = IMAGE_REQUEST_CODE.toInt()
        if(requestCode == code && resultCode == Activity.RESULT_OK){
            image.setImageURI(data?.data)
        }
    }



}