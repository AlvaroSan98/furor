package com.ifun.furor.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object ViewUtils {

    fun getDpFromResources(context: Context, dimension: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dimension * density).toInt()
    }

    fun hideSoftKeyboard(activity: Activity) {
        activity.currentFocus?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}