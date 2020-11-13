package com.sagheerhussainzardari.cheflab

import android.content.Context
import android.widget.Toast

fun Context.toastshort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastlong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}

