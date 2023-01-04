package com.example.covidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.covidapp.screens.home.activity.MainActivity
import com.lazday.sharedpreferences.helper.Constant
import com.lazday.sharedpreferences.helper.PrefHelper

class SplashActivity : AppCompatActivity() {

    lateinit var prefHelper: PrefHelper

    var usernamePre: String? =null
    private var login_status: Boolean? =false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prefHelper = PrefHelper(this)
        login_status = prefHelper.getBoolean( Constant.PREF_IS_LOGIN )
        login_status = prefHelper.getBoolean( Constant.PREF_IS_LOGIN )



        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        //Normal Handler is deprecated , so we have to change the code little bit

        // Handler().postDelayed({
        Handler(Looper.getMainLooper()).postDelayed({

            if (login_status as Boolean) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000) // 3000 is the delayed time in milliseconds.

    }
}