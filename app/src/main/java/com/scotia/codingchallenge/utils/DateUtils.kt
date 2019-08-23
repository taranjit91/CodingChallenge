package com.scotia.codingchallenge.utils

import android.util.Log
import java.text.SimpleDateFormat

object DateUtils {
    fun toSimpleDateString(date: String): String {
        Log.e("date", date)
        val inputPattern = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputPattern = SimpleDateFormat("MMM dd, yyyy hh:mm:ss a ")

        val newDate = inputPattern.parse(date)
        return outputPattern.format(newDate).toString()
    }
}