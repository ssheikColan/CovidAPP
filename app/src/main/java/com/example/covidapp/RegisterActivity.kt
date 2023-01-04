package com.example.covidapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.covidapp.R
import com.example.covidapp.databinding.ActivityLoginBinding
import com.example.covidapp.databinding.ActivityRegisterBinding
import com.example.covidapp.screens.home.activity.MainActivity
import com.lazday.sharedpreferences.helper.Constant
import com.lazday.sharedpreferences.helper.PrefHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    lateinit var prefHelper: PrefHelper

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prefHelper = PrefHelper(this)



        binding.loginBtn.setOnClickListener{

           val name= binding.emailEdit.text.toString()
            val password=binding.passwordEdit.text.toString()
            val phone=binding.fullnameEdit.text.toString()

                if (name.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty()) {
                    prefHelper.put( Constant.PREF_USERNAME, name)
                    prefHelper.put( Constant.PREF_PASSWORD,  password)
                    prefHelper.put( Constant.PREF_PHONE,  phone)
                    prefHelper.put( Constant.PREF_IS_LOGIN, true)

                    showMessage( "Register Success" )

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }else{
                    showMessage( "Please Fill data" )
                }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}