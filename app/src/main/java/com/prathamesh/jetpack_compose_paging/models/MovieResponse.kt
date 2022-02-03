package com.prathamesh.jetpack_compose_paging.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import javax.annotation.Nullable

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "data")
    val `data`: List<Movie>,
    @Json(name = "totalPages")
    val totalPages: Int,
    @Json(name = "totalPassengers")
    val totalPassengers: Int
)

@Entity(tableName = "movie")
@JsonClass(generateAdapter = true)
data class Movie(
    @PrimaryKey
    @Json(name = "_id")
    val id: String,
    @Json(name = "name")
    val name: String?,
    @Json(name = "trips")
    val trips: Int?,
    @Json(name = "__v")
    val v: Int
)

@JsonClass(generateAdapter = true)
data class Airline(
    @Json(name = "country")
    val country: String,
    @Json(name = "established")
    val established: String,
    @Json(name = "head_quaters")
    val headQuaters: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "logo")
    val logo: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "slogan")
    val slogan: String,
    @Json(name = "website")
    val website: String
)