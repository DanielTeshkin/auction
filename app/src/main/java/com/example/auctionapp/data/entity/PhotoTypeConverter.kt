package com.example.auctionapp.data.entity

import androidx.room.TypeConverter
import com.example.auctionapp.data.model.CitiesDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class PhotoTypeConverter {

    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromJson(json: String): List<Photo>? {
        val type = Types.newParameterizedType(List::class.java, Photo::class.java)
        val adapter = moshi.adapter<List<Photo>>(type)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun toJson(value: List<Photo>): String {
        val type = Types.newParameterizedType(List::class.java, Photo::class.java)
        val adapter = moshi.adapter<List<Photo>>(type)
        return adapter.toJson(value)
    }


    @TypeConverter
    fun fromJsonCity(json: String): CitiesDTO? {
        val adapter = moshi.adapter(CitiesDTO::class.java)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun toJsonCity(value: CitiesDTO?): String {
        val adapter = moshi.adapter(CitiesDTO::class.java)
        return adapter.toJson(value)
    }
}