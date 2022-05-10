package com.rkg.mandi.domain.model

import android.os.Parcelable
import com.rkg.mandi.data.model.MandiEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mandi(
    val id: Int
) : Parcelable

fun Mandi.toEntity() = MandiEntity(
    id = id
)