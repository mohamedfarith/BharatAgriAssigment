package com.app.bharatagriassigment.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import com.app.bharatagriassigment.GenericCallback
import com.app.bharatagriassigment.R

object LocalUtils {

    fun isOnline(context: Context?): Boolean {
        context?.let {
            val connectivityManager =
                it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        return false
    }

    fun showAlertDialog(
        context: Activity,
        title: String,
        message: String,
        callback: GenericCallback<String>
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialogInterface, which ->
            callback.callback("positive")
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


}