package com.wojtek.rx_java_mvvm_example.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wojtek.rx_java_mvvm_example.data.googleBook.VolumeInfo

class DataConverter {
    @TypeConverter
    fun fromVolumeList(value: ArrayList<VolumeInfo>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<VolumeInfo>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toVolumeList(value: String): ArrayList<VolumeInfo> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<VolumeInfo>>() {}.type
        return gson.fromJson(value, type)
    }
}