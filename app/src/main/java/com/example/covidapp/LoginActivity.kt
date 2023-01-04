package com.example.covidapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.covidapp.databinding.ActivityChartBinding
import com.example.covidapp.databinding.ActivityLoginBinding
import com.example.covidapp.screens.home.activity.ChartActivity
import com.example.covidapp.screens.home.activity.MainActivity
import com.lazday.sharedpreferences.helper.Constant
import com.lazday.sharedpreferences.helper.PrefHelper

class LoginActivity : AppCompatActivity() {

    lateinit var prefHelper: PrefHelper

    private lateinit var binding: ActivityLoginBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prefHelper = PrefHelper(this)

        binding.loginBtn.setOnClickListener{

            val username = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()

            val usernamePre = prefHelper.getString( Constant.PREF_USERNAME )
            val passwordPre = prefHelper.getString( Constant.PREF_PASSWORD )

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    if (username == usernamePre && password == passwordPre){
                        prefHelper.put( Constant.PREF_IS_LOGIN, true)
                        Toast.makeText(applicationContext,"Login Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                }else{

                        Toast.makeText(applicationContext,"Invalid Credentials ", Toast.LENGTH_SHORT).show()
                    }
            }else{
                Toast.makeText(applicationContext,"please fill data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerLink.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



    }
}