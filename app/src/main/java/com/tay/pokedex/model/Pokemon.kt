package com.tay.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class Pokemon(
    @PrimaryKey val name: String,
    val url: String
) : Serializable {
    fun getImgUrl(): String {
        val number = url.dropLast(1).substringAfterLast("/")
        return "https://pokeres.bastionbot.org/images/pokemon/$number.png"
    }
}