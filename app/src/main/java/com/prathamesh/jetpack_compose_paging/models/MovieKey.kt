package com.prathamesh.jetpack_compose_paging.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieKey(
    @PrimaryKey
    val movieId: String,
    val prevKey: Int?,
    val nextKey: Int?,
)
