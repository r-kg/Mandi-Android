package com.rkg.mandi.domain.model

import android.os.Parcelable
import com.rkg.mandi.data.model.MandiEntity
import com.rkg.mandi.presentation.model.MainItemModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mandi(
    val id: Int = 0,
    val title: String,
    val description: String,
    val updatedAt: Double? = null,
    val streakStartedAt: Double? = null
) : Parcelable

fun Mandi.toEntity() = MandiEntity(
    id = id,
    title = title,
    description = description,
    updatedAt = updatedAt,
    streakStartedAt = streakStartedAt
)

fun Mandi.toMandiItemModel() = MainItemModel.MandiItemModel(
    id = id,
    title = title,
    lastUpdated = updatedAt.toString(),
    streakCount = "0"
)

fun Mandi.updatePlant(currentTime: Double) = Mandi(
    id = id,
    title = title,
    description = description,
    updatedAt = currentTime,
    streakStartedAt = streakStartedAt ?: currentTime
)

fun Mandi.reset() = Mandi(
    id = id,
    title = title,
    description = description,
    updatedAt = null,
    streakStartedAt = null
)
