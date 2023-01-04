//package com.example.covidapp
//
//import android.app.AlertDialog
//import android.content.Intent
//import androidx.core.content.ContextCompat.startActivity
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class Dialog @Inject constructor() {
//
//
//    val builder = AlertDialog.Builder(this)
//    //set title for alert dialog
//    builder.setTitle(R.string.dialogTitle)
//    //set message for alert dialog
////        builder.setMessage(R.string.dialogMessage)
////        builder.setIcon(android.R.drawable.ic_dialog_alert)
//
//    //performing positive action
//    builder.setPositiveButton("Yes"){dialogInterface, which ->
//        val intent = Intent(this, LoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//    }
//    //performing cancel action
////        builder.setNeutralButton("Cancel"){dialogInterface , which ->
////            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
////        }
//    //performing negative action
//    builder.setNegativeButton("No"){dialogInterface, which ->
//
//    }
//    // Create the AlertDialog
//    val alertDialog: AlertDialog = builder.create()
//    // Set other dialog properties
//    alertDialog.setCancelable(false)
//    alertDialog.show()
//}