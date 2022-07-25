package com.example.aplikasivoucherku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.aplikasivoucherku.DatabaseHelper

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance text
        val txtNama: EditText = findViewById(R.id.editName)
        val txtEmail: EditText = findViewById(R.id.editEmail)
        val txtTelepon: EditText = findViewById(R.id.editTelepon)
        val txtAlamat: EditText = findViewById(R.id.editAlamat)
        val txtPassword: EditText = findViewById(R.id.editPassword)

        //instance button register
        val btnRegister: Button = findViewById(R.id.Register)

        //event button register
        btnRegister.setOnClickListener{

            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val email = txtEmail.text.toString().trim()
            val name = txtNama.text.toString().trim()
            val telepon = txtTelepon.text.toString().trim()
            val alamat = txtAlamat.text.toString().trim()
            val password = txtPassword.text.toString().trim()
            val level = 2

            //check data
            val data:String = databaseHelper.checkData(email)
            if(data == ""){
                //insert data
                databaseHelper.addAccount(email,
                    name, telepon, level,alamat, password)
                Toast.makeText(this@RegisterActivity, "Register Berhasil",
                    Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@RegisterActivity, "Email sudah digunakan" + data,
                    Toast.LENGTH_SHORT).show()
            }
        }

    }
}