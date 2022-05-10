package com.rkg.mandi.domain.model

import android.os.Parcelable
import com.rkg.mandi.data.model.MandiEntity
import com.rkg.mandi.presentation.model.MainItemModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mandi(
    val id: Int
) : Parcelable

fun Mandi.toEntity() = MandiEntity(
    id = id
)

fun Mandi.toMandiItemModel() = MainItemModel.MandiItemModel(
    id = id
)