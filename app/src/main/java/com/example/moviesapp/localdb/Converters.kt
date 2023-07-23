package com.example.moviesapp.localdb

import android.util.Log
import androidx.room.TypeConverter
import com.example.moviesapp.ui.details.ui.TAG

class Converters {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String = intList.toString()

    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        val result = ArrayList<Int>()
        val split = stringList.replace("[", "").replace("]", "").replace(" ", "").split(",")
        for (n in split) {
            try {
                result.add(n.toInt())
            } catch (e: Exception) {
                Log.d(TAG, "Converter error: $e")
            }
        }
        return result
    }
}