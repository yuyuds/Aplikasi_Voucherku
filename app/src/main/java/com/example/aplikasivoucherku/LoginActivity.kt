package com.example.aplikasivoucherku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.aplikasivoucherku.DatabaseHelper

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn:Button=findViewById(R.id.btnLogin)


        //hide title bar
        getSupportActionBar()?.hide()

        //instance text
        val txtUsername: EditText = findViewById(R.id.editTextEmail)
        val txtPassword:EditText = findViewById(R.id.editTextPassword)
        //instance button login
        val btnLogin: Button = findViewById(R.id.btnLogin)

        //event button login
        btnLogin.setOnClickListener {
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val email = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString().trim()
            val intent= Intent(this,MainActivity::class.java)


            //check login
            val result:Boolean = databaseHelper.checkLogin(email,password)
            if(result == true){
                Toast.makeText(this@LoginActivity, "Login Succes",
                    Toast.LENGTH_SHORT).show()
                val intentLogin = Intent(this@LoginActivity,
                    MainActivity::class.java)
                startActivity(intentLogin)
            }else{
                Toast.makeText(this@LoginActivity, "Login Failed, Try Again !!!",
                    Toast.LENGTH_SHORT).show()
            }



        }

        val registerLink = findViewById(R.id.linkRegister) as TextView

        registerLink.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}